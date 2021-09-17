import * as Interfaces from "../interfaces"

export const fetchProducts = async (): Promise<Interfaces.IProducts> => {
	const mockData: Interfaces.IProducts = {products: [{name: '1', img: '11'}, {name: '2', img: '12'}, {name: '3', img: '13'}] };
	// some await func
	return mockData;
}