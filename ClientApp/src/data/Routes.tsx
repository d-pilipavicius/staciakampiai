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
export const postProductModifierLink = address+"/v1/products/modifiers";
export const getProductModifierListLink = address+"/v1/products/modifiers";
export const putProductModifierLink = (id: string) => productModifier(id);
export const deleteProductModifierLink = (id: string) => productModifier(id);


function product(id: string) {
  return address+"/v1/products/"+id;
}

function productModifier(id: string) {
  return address+"/v1/products/modifiers/"+id
}