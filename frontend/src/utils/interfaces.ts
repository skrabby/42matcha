export interface IRule {
	rule: (args: IRuleArgs) => any;
	args?: IRuleArgs;
}

export interface IRuleArgs {
	value?: string;
	maxLength?: number;
	minLength?: number;
}