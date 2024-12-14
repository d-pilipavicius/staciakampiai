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

export interface GetProductsDTO extends PageinationDTO {
  items: ProductDTO[];
  businessId: string;
}

export interface PostProductDTO extends Omit<ProductDTO, 'id' | 'compatibleModifiers'> {
  compatibleModifierIds: string[]
}

export interface PutProductDTO extends Omit<ProductDTO, 'id' | 'businessId' | 'compatibleModifiers'> {
  compatibleModifierIds: string[]
}

//Product modifier
export interface ProductModifierDTO {
  id: string;
  title: string;
  quantityInStock: number;
  price: MoneyDTO;
  businessId: string;
}

export interface GetProductModifiersDTO extends PageinationDTO {
  items: ProductModifierDTO[]
}

export interface PostProductModifierDTO extends Omit<ProductModifierDTO, "id"> {
}

export interface PutProductModifierDTO extends Omit<ProductModifierDTO, "id" | "businessId"> {
}

//Pageination
export interface PageinationDTO {
  totalItems: number;
  totalPages: number;
  currentPage: number;
}

//General
export interface MoneyDTO {
  amount: number;
  currency: string;
}
