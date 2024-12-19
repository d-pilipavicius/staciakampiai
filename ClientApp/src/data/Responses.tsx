//Login / auth
export interface LoginDTO {
  username: string;
  password: string;
}

export interface LoginResponseDTO {
  accessToken: string;
  tokenType: string;
  user: UserDTO;
}

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
  valueType: PricingStrategy;
  currency: Currency;
  validFrom: string;
  validUntil: string;
  target: DiscountTarget;
  entitledProductIds: string[];
  businessId: string;
  usageCountLimit: number;
}

export interface PostDiscountDTO extends Omit<DiscountDTO, "id" | "usageCount" | "currency"> {
  currency: Currency | null;
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
  entitledProducts: ProductDTO[]
}

export interface PostTaxDTO extends Omit<TaxDTO, 'id' | 'entitledProducts'> {
  entitledProducts: string[]
}

export interface GetTaxesDTO extends PageinationDTO {
  items: TaxDTO[];
  businessId: string;
}

export interface PutTaxDTO extends Omit<TaxDTO, 'id' | 'businessId' | 'entitledProducts'> {
  entitledProducts: string[]
}

//Service charges
export interface ServiceChargeDTO {
  id: string;
  title: string;
  valueType: PricingStrategy;
  serviceChargeValue: number;
  currency: Currency;
  businessId: string;
}

export interface PostServiceChargeDTO extends Omit<ServiceChargeDTO, 'id'> {
}

export interface GetServiceChargeDTO extends PageinationDTO {
  items: ServiceChargeDTO[];
}

export interface PutServiceChargeDTO extends Partial<Omit<ServiceChargeDTO, 'id' | 'businessId'>> {
}

//Order
export interface OrderDTO {
  id: string;
  employeeId: string;
  reservationId: string | null;
  status: OrderStatus;
  createdAt: string;
  originalPrice: number;
  finalPrice: number;
  currency: Currency;
  items: OrderItemDTO[];
  serviceChargeIds: string[];
  serviceCharges: AppliedServiceChargeDTO[];
  businessId: string;
}

export interface PostOrderDTO extends Omit<OrderDTO, 'id' | 'createdAt' | 'status'> {

}

export interface GetOrdersDTO extends PageinationDTO {
  orders: OrderDTO[];
}

export interface AppliedServiceChargeDTO {
  id: string;
  chargedByEmployeeId: string;
  title: string;
  valueType: PricingStrategy;
  value: number;
  amount: number;
  currency: Currency;
  businessId: string;
}

export interface PutOrderDTO {
  reservationId: string;
  status: OrderStatus;
  items: OrderItemDTO[];
  serviceChargeIds: string;
  serviceCharges: AppliedServiceChargeDTO[];
}

export interface OrderItemDTO {
  id: string;
  productId: string;
  title: string;
  quantity: number;
  unitPrice: UnitPrice;
  originalPrice: number;
  currency: Currency;
  selectedModifierIds: string[];
  modifiers: OrderItemModifierDTO[];
}

export interface OrderItemModifierDTO {
  id: string;
  title: string;
  price: number;
  currency: Currency;
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
  currency: Currency;
}

export enum OrderStatus {
  NEW = "NEW", IN_PROGRESS = "IN_PROGRESS", CLOSED="CLOSED", CANCELED="CANCELED", RETURNED="RETURNED"
}

export enum Currency {
  EUR = "EUR" , USD = "USD"
}

export enum DiscountTarget {
  Entitled = "Entitled", All = "All"
}

export enum PricingStrategy {
  PERCENTAGE = "PERCENTAGE", FIXED_AMOUNT="FIXED_AMOUNT"
}

export interface UnitPrice {
  base: number;
  withModifiers: number;
}

//Payment
export interface AddTipDTO {
  orderId: string;
  employeeId: string;
  amount: number;
  currency: Currency;
}

export interface TipDTO {
  id: string;
  orderId: string;
  employeeId: string;
  amount: number;
  currency: Currency;
}

export interface GetTipDTO extends PageinationDTO {
  items: TipDTO[];
}

export interface CreatePaymentDTO {
  orderId: string;
  businessId: string;
  employeeId: string;
  currency: Currency;
  orderItems: OrderItemPaymentDTO[];
}

export interface OrderItemPaymentDTO {
  orderItemId: string;
  quantity: number;
}

export enum PaymentMethod {
  CARD, CASH
}
export enum PaymentStatus {
  PENDING, SUCCEEDED, FAILED, REFUNDED, CANCELED
}

export interface PaymentResponseDTO {
  id: string;
  orderId: string;
  orderItems: OrderItemPaymentDTO[];
  amount: number;
  currency: Currency;
  paymentMethod: PaymentMethod;
  paymentStatus: PaymentStatus;
  createdAt: string;
}

export interface CardPaymentResponseDTO{
  paymentId: string;
  checkoutUrl: string;
}

// Reservations
export interface ReservationDTO {
  id: string;
  customer: CustomerDTO;
  createdByEmployeeId: string;
  createdAt: string;
  reservationStartAt: string;
  reservationEndAt: string;
  businessId: string;
  serviceChargeIds: string[];
}

export interface CustomerDTO {
  firstName: string;
  lastName: String;
  phoneNumber: string;
}

export interface PostReservationDTO {
  customer: CustomerDTO;
  reservationStartAt: string;
  reservationEndAt: string;
  serviceChargeIds: string[];
  businessId: string;
}

export interface PutReservationDTO {
  reservationStartAt: string;
  reservationEndAt: string;
  customer: {
    firstName: string;
    lastName: string;
    phoneNumber: string;
  };
}

export interface GetReservationsDTO {
  totalItems: number;
  totalPages: number;
  currentPage: number;
  items: ReservationDTO[];
}