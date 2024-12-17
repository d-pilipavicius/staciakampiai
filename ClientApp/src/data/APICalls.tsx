import { MissingAuthError } from "./MissingAuthError";
import { BusinessDTO, DiscountDTO, GetProductModifiersDTO, GetProductsDTO, GetServiceChargeDTO, GetTaxesDTO, LoginDTO, LoginResponseDTO, OrderDTO, PostDiscountDTO, PostOrderDTO, PostProductDTO, PostProductModifierDTO, PostServiceChargeDTO, PostTaxDTO, ProductDTO, ProductModifierDTO, PutDiscountDTO, PutOrderDTO, PutProductDTO, PutProductModifierDTO, PutServiceChargeDTO, PutTaxDTO, ServiceChargeDTO, TaxDTO, UserDTO } from "./Responses";
import { addParam, deleteDiscountLink, deleteProductLink, deleteProductModifierLink, deleteServiceChargeLink, deleteTaxLink, getBusinessLink, getDiscountsLink, getGiftcardsLink, getOrderLink, getOrderReceiptLink, getOrdersLink, getProductListLink, getProductModifierListLink, getServiceChargeLink, getTaxLink, getUserLink, increaseDiscGiftUsageLink, loginLink, postDiscountLink, postOrderLink, postProductLink, postProductModifierLink, postServiceChargeLink, postTaxLink, putBusinessLink, putDiscountLink, putOrderLink, putProductLink, putProductModifierLink, putServiceChargeLink, putTaxLink } from "./Routes";

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
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await authAPI(getProductListLink+addParam({pageination, employeeId, businessId}), "GET", null, auth);

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
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await authAPI(postProductLink+addParam({employeeId}), "POST", JSON.stringify(product), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return response.json();
}

export async function putProductAPI(productId: string, product: PutProductDTO, auth: LoginResponseDTO): Promise<ProductDTO> {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await authAPI(putProductLink(productId)+addParam({employeeId}), "PUT", JSON.stringify(product), auth);
  
  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return response.json();
}

export async function deleteProductAPI(productId: string, auth: LoginResponseDTO) {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await authAPI(deleteProductLink(productId)+addParam({employeeId}), "DELETE", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Product modifiers
export async function postProductModifierAPI(dto: PostProductModifierDTO, auth: LoginResponseDTO): Promise<ProductModifierDTO> {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await authAPI(postProductModifierLink+addParam({employeeId}), "POST", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return response.json();
}

export async function getProductModifierAPI(pageNumber: number, pageSize: number, businessId: string, auth: LoginResponseDTO): Promise<GetProductModifiersDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize };
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await authAPI(getProductModifierListLink+addParam({businessId, employeeId, pageination}), "GET", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putProductModifierAPI(modifierId: string, dto: PutProductModifierDTO, auth: LoginResponseDTO): Promise<ProductModifierDTO> {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await authAPI(putProductModifierLink(modifierId)+addParam({employeeId}), "PUT", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return response.json();
}

export async function deleteProductModifierAPI(modifierId: string, auth: LoginResponseDTO) {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await authAPI(deleteProductModifierLink(modifierId)+addParam({employeeId}), "DELETE", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Discount / Giftcard
export async function postDiscountAPI(dto: PostDiscountDTO, auth: LoginResponseDTO): Promise<DiscountDTO> {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await authAPI(postDiscountLink+addParam({employeeId}), "POST", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getDiscountsAPI(pageNumber: number, pageSize: number, businessId: string, auth: LoginResponseDTO): Promise<DiscountDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize };
  const response = await authAPI(getDiscountsLink+addParam({pageination, businessId}), "GET", null, auth);

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
  const response = await authAPI(putDiscountLink(discountId)+addParam({employeeId}), "PUT", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function useDiscountAPI(discountId: string, auth: LoginResponseDTO): Promise<DiscountDTO> {
  const response = await authAPI(increaseDiscGiftUsageLink(discountId), "PUT", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function deleteDiscountAPI(discountId: string, auth: LoginResponseDTO) {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await authAPI(deleteDiscountLink(discountId)+addParam({employeeId}), "DELETE", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Tax
export async function postTaxAPI(dto: PostTaxDTO, auth: LoginResponseDTO): Promise<TaxDTO> {
  const response = await authAPI(postTaxLink, "POST", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getTaxesAPI(pageNumber: number, pageSize: number, auth: LoginResponseDTO): Promise<GetTaxesDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize };
  const response = await authAPI(getTaxLink+addParam({pageination}), "GET", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putTaxesAPI(taxId: string, dto: PutTaxDTO, auth: LoginResponseDTO) {
  const response = await authAPI(putTaxLink(taxId), "PUT", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function deleteTaxAPI(taxId: string, auth: LoginResponseDTO) {
  const response = await authAPI(deleteTaxLink(taxId), "DELETE", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Service charges
export async function postServiceChargeAPI(dto: PostServiceChargeDTO, auth: LoginResponseDTO): Promise<ServiceChargeDTO> {
  const response = await authAPI(postServiceChargeLink, "POST", JSON.stringify(dto), auth);

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
  const response = await authAPI(getServiceChargeLink+addParam({pageination}), "GET", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putServiceChargeAPI(serviceChargeId: string, dto: PutServiceChargeDTO, auth: LoginResponseDTO): Promise<ServiceChargeDTO> {
  const response = await authAPI(putServiceChargeLink(serviceChargeId), "PUT", JSON.stringify(dto), auth);
  
  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function deleteServiceChargeAPI(serviceChargeId: string, auth: LoginResponseDTO) {
  const response = await authAPI(deleteServiceChargeLink(serviceChargeId), "DELETE", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Order
export async function postOrderAPI(dto: PostOrderDTO, auth: LoginResponseDTO): Promise<OrderDTO> {
  const response = await authAPI(postOrderLink, "POST", JSON.stringify(dto), auth);

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
  const response = await authAPI(getOrderLink(orderId), "GET", null, auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putOrderAPI(orderId: string, dto: PutOrderDTO, auth: LoginResponseDTO): Promise<OrderDTO> {
  const response = await authAPI(putOrderLink(orderId), "PUT", JSON.stringify(dto), auth);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getOrderReceiptAPI(id: string, auth: LoginResponseDTO): Promise<OrderDTO> {
  const response = await authAPI(getOrderReceiptLink(id), "GET", null, auth);
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