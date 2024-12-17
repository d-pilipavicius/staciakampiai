import { PageinationDTO } from "./Responses";

const initialAddress = "http://localhost";
const apiPort = "8080";
export const address = initialAddress+":"+apiPort;

//Business links
export const postBusinessLink = address+"/v1/business";
export const getBusinessListLink = address+"/v1/business";
export const getBusinessLink = (id: string) => business(id);
export const putBusinessLink = (id: string) => business(id);
export const deleteBusinessLink = (id: string) => business(id);

function business(id: string) { 
  return address+"/v1/business/"+id;
}

//User links
export const postUserLink = address+"/v1/user";
export const getUserLink = (id: string) => user(id);
export const putUserLink = (id: string) => user(id);
export const deleteUserLink = (id: string) => user(id);

function user(id: string) { 
  return address+"/v1/user/"+id;
}

//Product links
export const postProductLink = address+"/v1/products";
export const getProductListLink = address+"/v1/products";
export const putProductLink = (id: string) => product(id);
export const deleteProductLink = (id: string) => product(id);

function product(id: string) {
  return address+"/v1/products/"+id;
}

//Product modifier links
export const postProductModifierLink = address+"/v1/products/modifiers";
export const getProductModifierListLink = address+"/v1/products/modifiers";
export const putProductModifierLink = (id: string) => productModifier(id);
export const deleteProductModifierLink = (id: string) => productModifier(id);

function productModifier(id: string) {
  return address+"/v1/products/modifiers/"+id
}

//Tax links
export const postTaxLink = address+"/v1/taxes";
export const getTaxLink = address+"/v1/taxes";
export const putTaxLink = (id: string) => tax(id);
export const deleteTaxLink = (id: string) => tax(id);

function tax(id: string) {
  return address+"/v1/taxes/"+id
}

//Service charges
export const postServiceChargeLink = address+"/v1/service-charges";
export const getServiceChargeLink = address+"/v1/service-charges";
export const putServiceChargeLink = (id: string) => serviceCharge(id);
export const deleteServiceChargeLink = (id: string) => serviceCharge(id);

function serviceCharge(id: string) {
  return address+"/v1/service-charges/"+id
}

//Discounts
export const postDiscountLink = address+"/v1/discounts";
export const getDiscountsLink = address+"/v1/discounts";
export const getGiftcardsLink = address+"/v1/discounts/giftcards";
export const putDiscountLink = (id: string) => discount(id);
export const deleteDiscountLink = (id: string) => discount(id);
export const increaseDiscGiftUsageLink = (id:string) => discount(id)+"/increaseUsage"; 

function discount(id: string) {
  return address+"/v1/discounts/"+id;
}

//Reservations
export const postReservationLink = address+"/v1/reservations";
export const getReservationLink = address+"/v1/reservations";
export const putReservationLink = (id: string) => reservation(id);
export const deleteReservationLink = (id: string) => reservation(id);

function reservation(id: string) {
  return address+"/v1/reservations/"+id;
}

//Orders
export const postOrderLink = address+"/v1/orders";
export const getOrdersLink = address+"/v1/orders";
export const getOrderLink = (id: string) => order(id);
export const putOrderLink = (id: string) => order(id);
export const getOrderReceiptLink = (id: string) => order(id)+"/receipt";

function order(id: string) {
  return address+`/v1/orders/${id}`;
}

//Payments

//General
interface AddParamParam {
  pageination?: {pageNumber: number, pageSize: number};
  employeeId?: string;
  businessId?: string
}

export function addParam({pageination, employeeId, businessId}: AddParamParam) {
  const count = (pageination ? 1 : 0) + (employeeId ? 1 : 0) + (businessId ? 1 : 0);
  if(count == 0)
    return ""
  var params = "?"
  var list = [];
  if(pageination)
    list.push(`pageNumber=${pageination.pageNumber}&pageSize=${pageination.pageSize}`);
  if(employeeId)
    list.push(`employeeId=${employeeId}`);
  if(businessId)
    list.push(`businessId=${businessId}`);
  list.forEach((el, index) => {
    params += el;
    if(index+1 != list.length)
      params += "&";
  });
  return params;
}