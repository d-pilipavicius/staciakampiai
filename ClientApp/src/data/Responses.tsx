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

//Discount
export interface DiscountDTO {
  id: string;
  usageCount: number;
  code: string;
  amount: number;
  valueType: 'Percentage' | 'FixedAmount';
  currency: 'EUR' | 'USD';
  validFrom: string;
  validUntil: string;
  target: 'Entitled' | 'All';
  entitledProductsIds: string[];
  businessId: string;
  usageCountLimit: number;
}

export interface PostDiscountDTO extends Omit<DiscountDTO, "id"> {
}

export interface GetDiscountsDTO extends PageinationDTO {
  items: DiscountDTO[];
}

export interface PutDiscountDTO extends Omit<DiscountDTO, "id" | "usageCount" | "businessId" | "usageCountLimit"> {
}

//Tax 
export interface TaxDTO {
  id: string;
  title: string;
  ratePercentage: number;
  businessId: string;
}

export interface PostTaxDTO extends Omit<TaxDTO, "id"> {
}

export interface GetTaxesDTO extends PageinationDTO {
  items: TaxDTO[];
}

export interface PutTaxDTO extends Partial<Omit<TaxDTO, "id" | "businessId">> {
}

//Service charges
export interface ServiceChargeDTO {
  id: string;
  title: string;
  valueType: 'PERCENTAGE' | 'FIXED_AMOUNT';
  serviceChargeValue: number;
  currency: 'EUR' | 'USD';
  businessId: string;
}

export interface PostServiceChargeDTO extends Omit<ServiceChargeDTO, 'id'> {
}

export interface GetServiceChargeDTO extends PageinationDTO {
  items: ServiceChargeDTO[];
}

export interface PutServiceChargeDTO extends Partial<Omit<ServiceChargeDTO, 'id' | 'businessId'>> {
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
  currency: 'EUR' | 'USD';
}