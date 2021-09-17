import * as Interfaces from './interfaces';
import {BaseSyntheticEvent} from "react";

export const validInputCheck = (fields: any): boolean => {
	for (let key of Object.keys(fields)) {
		for (let rule of fields[key].rules || []) {
			// invoking rule checker, if length > 0 there is error message
			if (!rule.args) {
				rule.args = { value: fields[key].value || '' }
			} else {
				rule.args.value = fields[key].value || '';
			}
			if (rule.rule(rule.args).length > 0) {
				return false;
			}
		}
	}
	return true;
}

export const isRequired = (args: Interfaces.IRuleArgs): string => {
	return (!args.value || args.value === '') ? 'Field cannot be empty' : '';
}

export const maxLength = (args: Interfaces.IRuleArgs): string => {
	return (args.value!.length > args.maxLength! ? `Length cannot exceed ${args.maxLength} letters` : '');
}

export const minLength = (args: Interfaces.IRuleArgs): string => {
	return (args.value!.length <= args.minLength! ? `Length should exceed ${args.minLength} letters` : '');
}

export const hasCapLetter = (args: Interfaces.IRuleArgs): string => {
	return (/[A-Z]/.test(args.value!) ? '' : 'Field should contain 1 capital letter')
}

export const hasDigit = (args: Interfaces.IRuleArgs): string => {
	return (/\d/.test(args.value!) ? '' : 'Field should contain 1 digit')
}