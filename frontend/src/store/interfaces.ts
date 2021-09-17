export interface IAction {
	type: string,
	payload: any
}

export interface IProduct {
	name: string
	img: string
}

export interface IProducts {
	products: IProduct[]
}
