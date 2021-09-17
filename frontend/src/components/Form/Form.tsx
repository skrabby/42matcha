import * as React from 'react';
import * as Components from '../../components';
import * as Rules from '../../utils/rules';
import {BaseSyntheticEvent} from "react";

interface IFormProps extends React.ButtonHTMLAttributes<HTMLButtonElement>  {
    onSubmit?: (e: any) => any;
    children: React.ReactNode;
    fields: any;
}

interface IFormState {
    isChanged: boolean;
    // JSX elements
    fields: any;
    // Storage of rules and values
    fieldRules: any;
}

export default class Form extends React.Component<IFormProps, IFormState> {
    state = {
        isChanged: false,
        fields: this.props.fields.props.children,
        fieldRules: undefined
    }

    componentDidMount() {
        const { fields } = this.state;
        const formFields: any = {};

        React.Children.forEach(fields, (field) => {
            formFields[field.props.id] = { rules: field.props.rules, value: undefined }
        })

        this.setState({
            fieldRules: formFields
        })
    }

    onFormSubmit = (e: BaseSyntheticEvent) => {
        e.preventDefault();
        // Invoking error checker
        this.setState({isChanged: true});

        // Checking for field errors
        if (!Rules.validInputCheck(this.state.fieldRules)) {
            return ;
        }

        this.props.onSubmit && this.props.onSubmit(e);
    }

    render() {
        const {
            children,
        } = this.props;

        const {
            isChanged,
            fields,
        } = this.state;

        return (
            <form
                  onSubmit={this.onFormSubmit}>
                { React.Children.map(fields, (field: React.ReactNode) => {
                    if (React.isValidElement(field)) {
                        return React.cloneElement(field, {
                            isChanged: isChanged,
                            onFieldChange: (e: any) => {
                                const fieldRules: any = this.state.fieldRules;
                                fieldRules[field.props.id].value = e.value;
                                this.setState({ fieldRules: fieldRules })
                            }
                        })
                    }
                    return field;
                }) }
                { children }
            </form>
        );
    }
}