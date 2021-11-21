const MongoClient = require("mongodb").MongoClient;

const url = "mongodb://root:example@localhost:27017/";
const mongoClient = new MongoClient(url);

// Подключаемся к серверу
mongoClient.connect(function(err, client){

    // обращаемся к базе данных admin
    const db = client.db("admin");

    db.command({ping: 1}, function(err, result){
        if(!err){
            console.log("Подключение с сервером успешно установлено");
            console.log(result);
        }
        // Закрываем подключение
        client.close();
        console.log("Подключение закрыто");
    });
});

module.exports = mongoClient;
