//User
export interface UserDTO {
  id: string;
  fullName: string;
  phoneNumber: string;
  emailAddress: string;
  businessId: string;
  role: string;
}

//Business
export interface BusinessDTO {
  id: string;
  ownerId: string;
  name: string;
  phoneNumber: string;
  emailAddress: string;
  address: string;
}

//Product
export interface ProductDTO {
  id: string;
  title: string;
  quantityInStock: number;
  price: MoneyDTO;
  compatibleModifiers: ProductModifierDTO[];
  businessId: string;
}

export interface PostProductDTO extends Omit<ProductDTO, 'id'> {
}

export interface PutProductDTO extends Omit<ProductDTO, 'id' | 'businessId'> {

}

export interface ProductModifierDTO {
  id: string;
  title: string;
  quantityInStock: number;
  price: MoneyDTO;
  businessId: string;
}

export interface MoneyDTO {
  amount: number;
  currency: string;
}

export interface GetProductsDTO extends PageinationDTO {
  items: ProductDTO[];
  businessId: string;
}



//Pageination
export interface PageinationDTO {
  totalItems: number;
  totalPages: number;
  currentPage: number;
}