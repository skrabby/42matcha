"use strict";

const WebSocket = require('ws');
const { WebSocketServer } = require('ws');
const { v4: uuidv4 } = require('uuid');
const mongoClient = require('./mongo-client');
var webSocketsServerPort = 1337;

let ROOMS = { };

let wss = new WebSocketServer({
    port: webSocketsServerPort,
    perMessageDeflate: {
        zlibDeflateOptions: {
            // See zlib defaults.
            chunkSize: 1024,
            memLevel: 7,
            level: 3
        },
        zlibInflateOptions: {
            chunkSize: 10 * 1024
        },
        // Other options settable:
        clientNoContextTakeover: true, // Defaults to negotiated value.
        serverNoContextTakeover: true, // Defaults to negotiated value.
        serverMaxWindowBits: 10, // Defaults to negotiated value.
        // Below options specified as default values.
        concurrencyLimit: 10, // Limits zlib concurrency for perf.
        threshold: 1024 // Size (in bytes) below which messages
        // should not be compressed if context takeover is disabled.
    }
});

wss.on('connection', (ws, req) => {
    const params = getJsonFromUrl(req.url);
    ws.uuid = uuidv4();

    // chats page
    if (params.chats) {
        try {
            const chatIds = JSON.parse(params.chats);
            ws.chatIds = chatIds;
            if (Array.isArray(chatIds)) {
                chatIds.forEach((chatId) => {
                    if (ROOMS[chatId]) {
                        // if room exists add participant
                        ROOMS[chatId][ws.uuid] = ws;
                    } else {
                        ROOMS[chatId] = {};
                        ROOMS[chatId][ws.uuid] = ws;
                    }
                });

                mongoClient.connect(async (error, client) => {
                    const messages = [];
                    chatIds.forEach((chatId) => {
                        messages.push(client.db("matcha").collection("chats")
                            .findOne({chatId: chatId}, {sort: {$natural: -1}}));
                    })
                    ws.send(JSON.stringify((await Promise.all(messages)).filter(message => message !== null)));
                });
            }
        } catch (e) {}

    // chat page
    } else if (params.chatId) {
        ws.chatIds = [params.chatId]

        if (ROOMS[params.chatId]) {
            // if room exists add participant
            ROOMS[params.chatId][ws.uuid] = ws;
        } else {
            ROOMS[params.chatId] = {};
            ROOMS[params.chatId][ws.uuid] = ws;
        }

        mongoClient.connect((error, client) => {
            client.db("matcha").collection("chats")
                .find({chatId: params.chatId})
                .toArray((err, messages) => {
                    ws.send(JSON.stringify(messages.slice(-100)))
                });
        })
    }

    ws.on('message', (message) => {
        const messageJSON = JSON.parse(message.toString());
        const today = new Date();
        const updMsg = {
            chatId: messageJSON.chatId,
            message: {
                messageId: uuidv4(),
                text: messageJSON.message,
                authorId: messageJSON.authorId,
                authorName: messageJSON.authorName,
                createdAt: today
            }
        };

        mongoClient.connect((error, client) => {
            client.db("matcha").collection("chats").insertOne(updMsg);
        });

        // broadcast to all participants of chat room
        Object.entries(ROOMS[messageJSON.chatId]).forEach(([, ws]) => {
            ws.send(JSON.stringify(updMsg));
        })
    });

    ws.on('close', () => {
        ws.chatIds.forEach((chatId) => {
            if (!ROOMS[ws.chatId] || !ROOMS[ws.chatId][ws.uuid]) return;
            if (Object.keys(ROOMS[ws.chatId]).length === 1) delete ROOMS[ws.chatId]
            else delete ROOMS[ws.chatId][ws.uuid];
        })
    })
})


function getJsonFromUrl(url) {
    if (!url) url = location.search;
    let query = url.substr(2);
    let result = {};
    query.split("&").forEach(function(part) {
        let item = part.split("=");
        result[item[0]] = decodeURIComponent(item[1]);
    });
    return result;
}
