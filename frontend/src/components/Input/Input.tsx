import * as React from 'react';
import './Input.scss';
import { Message } from '../../utils/enums';
import * as Interfaces from '../../utils/interfaces';

type Exclude = 'onChange' | 'value';

interface IInputProps extends Omit<React.ButtonHTMLAttributes<HTMLInputElement>, Exclude> {
    value?: string;
    placeholder?: string;
    className?: string;
    error?: boolean;
    isDisabled?: boolean;
    type?: any;
    icon?: string;
    msgtype?: Message,
    msg?: string;
    rules?: Interfaces.IRule[];
    isChanged?: boolean;

    onChange?(e: EventTarget & HTMLInputElement): void;
    onFieldChange?(e: EventTarget & HTMLInputElement): void;
}

interface IInputState {
    value: any;
    rules: Interfaces.IRule[];
    msgtype: Message;
    msg: string;
    isChanged: boolean;
    errorsToRender: JSX.Element[]
}

export default class Input extends React.Component<IInputProps, IInputState> {
    state = {
        value: this.props.value || '',
        rules: this.props.rules || [], 
        msgtype: this.props.msgtype || Message.NONE,
        msg: this.props.msg || '',
        isChanged: this.props.isChanged || false,
        errorsToRender: []
    };

    componentDidMount() {
        const { isChanged, value } = this.props;

        isChanged && this.setErrorsToRender(value || '');
    }

    componentDidUpdate(prevProps: Readonly<IInputProps>, prevState: Readonly<IInputState>, snapshot?: any) {
        const { isChanged } = this.props;
        const { value } = this.state;

        if (isChanged !== prevProps.isChanged && isChanged) {
            this.setErrorsToRender(value);
        }
    }

    render() {
        const {
            placeholder,
            className,
            type,
            icon,
        } = this.props;

        const {
            isChanged,
            errorsToRender
        } = this.state;

        return (
            <div className={`input ${className}`}>
                <div className={icon ? `input-icon ${icon}` : ''}></div>
                <input
                    type={type || 'text'}
                    value={this.state.value}
                    onChange={this.onChange}
                    placeholder={placeholder}
                />
                <div className='msg-block'>
                    { this.state.msgtype === Message.INFO ? <div className='input-msg info-msg'>{this.state.msg}</div> : '' }
                    { this.state.msgtype === Message.ERROR ? <div className='input-msg error-msg'>{this.state.msg}</div> : '' }
                    { this.state.msgtype === Message.SUCCESS ? <div className='input-msg success-msg'>{this.state.msg}</div> : '' }
                    { isChanged && errorsToRender }
                </div>
            </div>
        );
    }

    onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const target = e.target;
        this.setErrorsToRender(target.value);

        this.props.onChange && this.props.onChange(target);
        this.props.onFieldChange && this.props.onFieldChange(target);
    };

    getErrorsToRender(value: string) {
        const { rules } = this.state;

        let errorsToRender: JSX.Element[] = [];

        rules && rules.forEach((rule: Interfaces.IRule, idx: number) => {
            const args: Interfaces.IRuleArgs = rule.args ? rule.args : {} ;
            args.value = value;
            const errorMsg = rule.rule(args);
            if (errorMsg.length > 0) {
                errorsToRender.push(<div key={`msg-${idx}`} className='input-msg-container'>
                    <div className='input-msg-icon error-msg'></div>
                    <div className='input-msg error-msg'>{errorMsg}</div>
                </div>);
            }
        });

        return errorsToRender;
    }

    setErrorsToRender(value: string) {
        const errorsToRender = this.getErrorsToRender(value)

        this.setState({
            value: value,
            isChanged: true,
            errorsToRender: errorsToRender
        });
    }
}