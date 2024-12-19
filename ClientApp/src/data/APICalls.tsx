import { MissingAuthError } from "./MissingAuthError";
import {
  BusinessDTO,
  DiscountDTO,
  GetDiscountsDTO,
  GetProductModifiersDTO,
  GetProductsDTO, GetReservationsDTO,
  GetServiceChargeDTO,
  GetTaxesDTO,
  LoginDTO,
  LoginResponseDTO,
  OrderDTO,
  PostDiscountDTO,
  PostOrderDTO,
  PostProductDTO,
  PostProductModifierDTO,
  PostReservationDTO,
  PostServiceChargeDTO,
  PostTaxDTO,
  ProductDTO,
  ProductModifierDTO,
  PutDiscountDTO,
  PutOrderDTO,
  PutProductDTO,
  PutProductModifierDTO,
  PutServiceChargeDTO,
  PutTaxDTO,
  ServiceChargeDTO,
  TaxDTO,
  UserDTO
} from "./Responses";
import {
  addParam,
  deleteDiscountLink,
  deleteProductLink,
  deleteProductModifierLink,
  deleteServiceChargeLink,
  deleteTaxLink,
  getBusinessLink,
  getDiscountsLink,
  getGiftcardsLink,
  getOrderLink,
  getOrderReceiptLink,
  getOrdersLink,
  getProductListLink,
  getProductModifierListLink,
  getReservationLink,
  getServiceChargeLink,
  getTaxLink,
  getUserLink,
  increaseDiscGiftUsageLink,
  loginLink,
  postDiscountLink,
  postOrderLink,
  postProductLink,
  postProductModifierLink, postReservationLink,
  postServiceChargeLink,
  postTaxLink,
  putBusinessLink,
  putDiscountLink,
  putOrderLink,
  putProductLink,
  putProductModifierLink,
  putServiceChargeLink,
  putTaxLink
} from "./Routes";

//Login / Authentication
export async function loginAPI(login: LoginDTO): Promise<LoginResponseDTO> {
  const response = await basicAPI(loginLink, "POST", JSON.stringify(login));

  if(!response.ok) 
    throw new Error(`Error ${response.status}: ${response.text}`);
  
  return response.json();
}

//Products
export async function getBusinessProductsAPI(pageNumber: number, pageSize: number, businessId: string, auth: LoginResponseDTO): Promise<GetProductsDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize };
  const response = await authAPI(getProductListLink(auth.user.businessId)+addParam({pageination, businessId}), "GET", null, auth);

  if(!response.ok) {
    if(response.status == 500) {
      return {
        "businessId": "",
        "totalPages": 0,
        "totalItems": 0,
        "currentPage": 0,
        "items": []
      }
    }
    else 
      throw new Error(`Error ${response.status}: ${response.text}`);
  }

  const data: GetProductsDTO = await response.json();
  return data;
}

export async function postProductAPI(product: PostProductDTO, auth: LoginResponseDTO): Promise<ProductDTO> {
  const response = await authAPI(postProductLink(auth.user.businessId), "POST", JSON.stringify(product), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return response.json();
}

export async function putProductAPI(productId: string, product: PutProductDTO, auth: LoginResponseDTO): Promise<ProductDTO> {
  const response = await authAPI(putProductLink(auth.user.businessId, productId), "PUT", JSON.stringify(product), auth);
  
  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return response.json();
}

export async function deleteProductAPI(productId: string, auth: LoginResponseDTO) {
  const response = await authAPI(deleteProductLink(auth.user.businessId, productId), "DELETE", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Product modifiers
export async function postProductModifierAPI(dto: PostProductModifierDTO, auth: LoginResponseDTO): Promise<ProductModifierDTO> {
  const response = await authAPI(postProductModifierLink(auth.user.businessId), "POST", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return response.json();
}

export async function getProductModifierAPI(pageNumber: number, pageSize: number, businessId: string, auth: LoginResponseDTO): Promise<GetProductModifiersDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize };
  const response = await authAPI(getProductModifierListLink(auth.user.businessId)+addParam({businessId, pageination}), "GET", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putProductModifierAPI(modifierId: string, dto: PutProductModifierDTO, auth: LoginResponseDTO): Promise<ProductModifierDTO> {
  const response = await authAPI(putProductModifierLink(auth.user.businessId, modifierId), "PUT", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return response.json();
}

export async function deleteProductModifierAPI(modifierId: string, auth: LoginResponseDTO) {
  const response = await authAPI(deleteProductModifierLink(auth.user.businessId, modifierId), "DELETE", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Discount / Giftcard
export async function postDiscountAPI(dto: PostDiscountDTO, auth: LoginResponseDTO): Promise<DiscountDTO> {
  console.log(JSON.stringify(dto));
  const response = await authAPI(postDiscountLink(auth.user.businessId), "POST", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getDiscountsAPI(pageNumber: number, pageSize: number, businessId: string, auth: LoginResponseDTO): Promise<GetDiscountsDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize };
  const response = await authAPI(getDiscountsLink(auth.user.businessId)+addParam({pageination, businessId}), "GET", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getGiftcardsAPI(pageNumber: number, pageSize: number, businessId: string, auth: LoginResponseDTO): Promise<DiscountDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize };
  const response = await authAPI(getGiftcardsLink+addParam({pageination, businessId}), "GET", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putDiscountAPI(discountId: string, dto: PutDiscountDTO, auth: LoginResponseDTO): Promise<DiscountDTO>{
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await authAPI(putDiscountLink(auth.user.businessId, discountId), "PUT", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function useDiscountAPI(discountId: string, auth: LoginResponseDTO): Promise<DiscountDTO> {
  const response = await authAPI(increaseDiscGiftUsageLink(auth.user.businessId, discountId), "PUT", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function deleteDiscountAPI(discountId: string, auth: LoginResponseDTO) {
  const response = await authAPI(deleteDiscountLink(auth.user.businessId, discountId), "DELETE", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Tax
export async function postTaxAPI(dto: PostTaxDTO, auth: LoginResponseDTO): Promise<TaxDTO> {
  const response = await authAPI(postTaxLink(auth.user.businessId), "POST", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getTaxesAPI(pageNumber: number, pageSize: number, businessId: string, auth: LoginResponseDTO): Promise<GetTaxesDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize };
  const response = await authAPI(getTaxLink(auth.user.businessId)+addParam({pageination, businessId}), "GET", null, auth);

  if (!response.ok) {
    if(response.status == 500){
      return {
        "businessId": "",
        "totalPages": 0,
        "totalItems": 0,
        "currentPage": 0,
        "items": []
      }
    }
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  const data: GetTaxesDTO = await response.json();
  return data;
}

export async function putTaxesAPI(taxId: string, dto: PutTaxDTO, auth: LoginResponseDTO) {
  const response = await authAPI(putTaxLink(auth.user.businessId, taxId), "PUT", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function deleteTaxAPI(taxId: string, auth: LoginResponseDTO) {
  const response = await authAPI(deleteTaxLink(auth.user.businessId, taxId), "DELETE", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Service charges
export async function postServiceChargeAPI(dto: PostServiceChargeDTO, auth: LoginResponseDTO): Promise<ServiceChargeDTO> {
  const response = await authAPI(postServiceChargeLink(auth.user.businessId), "POST", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getServiceChargesAPI(pageNumber: number, pageSize: number, auth: LoginResponseDTO): Promise<GetServiceChargeDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize
  }
  const response = await authAPI(getServiceChargeLink(auth.user.businessId)+addParam({pageination}), "GET", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putServiceChargeAPI(serviceChargeId: string, dto: PutServiceChargeDTO, auth: LoginResponseDTO): Promise<ServiceChargeDTO> {
  const response = await authAPI(putServiceChargeLink(auth.user.businessId, serviceChargeId), "PUT", JSON.stringify(dto), auth);
  
  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function deleteServiceChargeAPI(serviceChargeId: string, auth: LoginResponseDTO) {
  const response = await authAPI(deleteServiceChargeLink(auth.user.businessId, serviceChargeId), "DELETE", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Order
export async function postOrderAPI(dto: PostOrderDTO, auth: LoginResponseDTO): Promise<OrderDTO> {
  const response = await authAPI(postOrderLink(auth.user.businessId), "POST", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getOrdersAPI(pageNumber: number, pageSize: number, auth: LoginResponseDTO): Promise<OrderDTO> {
  const pageination = {
    pageNumber: pageNumber+1,
    pageSize: pageSize
  };
  const response = await authAPI(getOrdersLink+addParam({pageination}), "GET", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getOrderAPI(orderId: string, auth: LoginResponseDTO): Promise<OrderDTO> {
  const response = await authAPI(getOrderLink(auth.user.businessId, orderId), "GET", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putOrderAPI(orderId: string, dto: PutOrderDTO, auth: LoginResponseDTO): Promise<OrderDTO> {
  const response = await authAPI(putOrderLink(auth.user.businessId, orderId), "PUT", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getOrderReceiptAPI(id: string, auth: LoginResponseDTO): Promise<OrderDTO> {
  const response = await authAPI(getOrderReceiptLink(auth.user.businessId, id), "GET", null, auth);
  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}


//User
export async function getUserAPI(userId: string, auth: LoginResponseDTO): Promise<UserDTO> {
  const response = await authAPI(getUserLink(userId), "GET", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}


//Business
export async function getBusinessAPI(id: string, auth: LoginResponseDTO): Promise<BusinessDTO> {
  const response = await authAPI(getBusinessLink(id), "GET", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putBusinessAPI(business: BusinessDTO, auth: LoginResponseDTO): Promise<BusinessDTO> {
  const response = await authAPI(putBusinessLink(business.id), "PUT", JSON.stringify(business), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}


//Custom API sending method
async function basicAPI(url: string, method: string, body: string | null) {
  const response = fetch(url, {
    method: method,
    headers: {
      "Content-Type": "application/json",
    },
    body: body
  })

  return response;
}

async function authAPI(url: string, method: string, body: string | null, auth: LoginResponseDTO) {
  console.log(`${auth.tokenType}${auth.accessToken}`)
  const response = await fetch(url, {
    method: method,
    headers: {
      "Content-Type": "application/json",
      "Authorization": `${auth.tokenType}${auth.accessToken}`
    },
    body: body
  })

  if(response.status == 401)
    throw new MissingAuthError("Authentication required.");

  return response;
}

// Reservations
export async function getReservationsAPI(businessId: string, page: number, pageSize: number, auth: LoginResponseDTO): Promise<GetReservationsDTO> {
  const response = await authAPI(
      `${getReservationLink(businessId)}?pageNumber=${page}&pageSize=${pageSize}`,
      "GET",
      null,
      auth
  );

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${await response.text()}`);
  }

  return response.json();
}

export async function postReservationAPI(reservation: PostReservationDTO, auth: LoginResponseDTO): Promise<void> {
  const response = await authAPI(
      postReservationLink(reservation.businessId, auth.user.id),
      "POST",
      JSON.stringify(reservation),
      auth
  );

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${await response.text()}`);
  }
}



