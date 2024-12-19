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
export const loginLink = address+"/v1/user/login";
export const postUserLink = address+"/v1/user";
export const getUserLink = (id: string) => user(id);
export const putUserLink = (id: string) => user(id);
export const deleteUserLink = (id: string) => user(id);

function user(id: string) { 
  return address+"/v1/user/"+id;
}

//Product links
export const postProductLink = (businessId: string) => product(businessId);
export const getProductListLink = (businessId: string) => product(businessId);
export const putProductLink = (businessId: string, productId: string) => productById(businessId, productId);
export const deleteProductLink = (businessId: string, productId: string) => productById(businessId, productId);

function product(businessId: string) {
  return address+`/v1/products/${businessId}`;
}

function productById(businessId: string, productId: string) {
  return product(businessId)+`/${productId}`;
}

//Product modifier links
export const postProductModifierLink = (businessId: string) => productModifier(businessId);
export const getProductModifierListLink = (businessId: string) => productModifier(businessId);
export const putProductModifierLink = (businessId: string, productId: string) => productModifierById(businessId, productId);
export const deleteProductModifierLink = (businessId: string, productId: string) => productModifierById(businessId, productId);

function productModifierById(businessId: string, productId: string) {
  return productModifier(businessId)+`/${productId}`;
}

function productModifier(businessId: string) {
  return address+`/v1/products/${businessId}/modifiers`;
}
//Tax links
export const postTaxLink = (businessId: string) => tax(businessId);
export const getTaxLink = (businessId: string) => tax(businessId);
export const putTaxLink = (businessId: string, taxId: string) => taxById(businessId, taxId);
export const deleteTaxLink = (businessId: string, taxId: string) => taxById(businessId, taxId);

function tax(id: string) {
  return address+`/v1/taxes/${id}`;
}

function taxById(id: string, taxId: string) {
  return tax(id)+`/${taxId}`;
}

//Service charges
export const postServiceChargeLink = serviceCharge;
export const getServiceChargeLink = serviceCharge;
export const putServiceChargeLink = serviceChargeById;
export const deleteServiceChargeLink = serviceChargeById;

function serviceCharge(businessId: string) {
  return address+`/v1/service-charges/${businessId}`;
}

function serviceChargeById(businessId: string, serChId: string) {
  return serviceCharge(businessId)+`/${serChId}`;
}

//Discounts
export const postDiscountLink = discount;
export const getDiscountsLink = discount;
export const getGiftcardsLink = (businessId: string) => discount(businessId)+"/giftcards";
export const putDiscountLink = discountById;
export const deleteDiscountLink = discountById;
export const increaseDiscGiftUsageLink = (businessId: string, discountId: string) => discountById(businessId,discountId)+"/increaseUsage"; 

function discount(businessId: string) {
  return address+`/v1/discounts/${businessId}`;
}

function discountById(businessId: string, discountId: string) {
  return discount(businessId)+`/${discountId}`;
}

//Reservations
export const postReservationLink = postReservation;
export const getReservationLink = reservation;
export const putReservationLink = reservationById;
export const deleteReservationLink = reservationById;

function reservation(businessId: string) {
  return address+`/v1/reservations/${businessId}`;
}

function postReservation(businessId: string, employeeId: string){
  return `${reservation(businessId)}?employeeId=${encodeURIComponent(employeeId)}`;
}

function reservationById(businessId: string, reservationId: string) {
  return reservation(businessId)+`/${reservationId}`;
}

//Orders
export const postOrderLink = order;
export const getOrdersLink = order;
export const getOrderLink = orderById;
export const putOrderLink = orderById
export const getOrderReceiptLink = (businessId: string, orderId: string) => orderById(businessId,orderId)+"/receipt";

function order(businessId: string) {
  return address+`/v1/orders/${businessId}`;
}

function orderById(businessId: string, orderId: string) {
  return order(businessId)+`/${orderId}`;
}

//Payments
export const postTipLink = (businessId: string) => address+`/v1/payments/${businessId}/tips`;
export const getTipsLink = (businessId: string) => address+`/v1/payments/${businessId}/tips`;

export const postCardPaymentLink = (businessId: string) => address+`/v1/payments/${businessId}/card`;
export const postCashPaymentLink = (businessId: string) => address+`/v1/payments/${businessId}/cash`;
export const postInitiateRefund = (businessId: string, paymentId: string) => address+`/v1/payments/${businessId}/${paymentId}/refund`;

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