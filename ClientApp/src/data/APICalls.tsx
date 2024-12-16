import { BusinessDTO, DiscountDTO, GetProductModifiersDTO, GetProductsDTO, GetServiceChargeDTO, GetTaxesDTO, OrderDTO, PostDiscountDTO, PostOrderDTO, PostProductDTO, PostProductModifierDTO, PostServiceChargeDTO, PostTaxDTO, ProductDTO, ProductModifierDTO, PutDiscountDTO, PutOrderDTO, PutProductDTO, PutProductModifierDTO, PutServiceChargeDTO, PutTaxDTO, ServiceChargeDTO, TaxDTO, UserDTO } from "./Responses";
import { addParam, deleteDiscountLink, deleteProductLink, deleteProductModifierLink, deleteServiceChargeLink, deleteTaxLink, getBusinessLink, getDiscountsLink, getGiftcardsLink, getOrderLink, getOrderReceiptLink, getOrdersLink, getProductListLink, getProductModifierListLink, getServiceChargeLink, getTaxLink, getUserLink, increaseDiscGiftUsageLink, postDiscountLink, postOrderLink, postProductLink, postProductModifierLink, postServiceChargeLink, postTaxLink, putBusinessLink, putDiscountLink, putOrderLink, putProductLink, putProductModifierLink, putServiceChargeLink, putTaxLink } from "./Routes";

export async function getMyBusiness(): Promise<BusinessDTO> {
  const businessId = localStorage.getItem("userBusinessId");
  if(businessId) 
    return await getBusinessAPI(businessId);
  else 
    throw new Error(`Error: Bad login!`);
}

export async function getMyBusinessProducts(pageNumber: number, pageSize: number): Promise<GetProductsDTO> {
  const businessId = localStorage.getItem("userBusinessId");
  if(businessId) 
    return await getBusinessProductsAPI(pageNumber, pageSize, businessId);
  else 
    throw new Error(`Error: Bad login!`);
}


//Products
export async function getBusinessProductsAPI(pageNumber: number, pageSize: number, businessId: string): Promise<GetProductsDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize };
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await fetch(getProductListLink+addParam({pageination, employeeId, businessId}));

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

export async function postProductAPI(product: PostProductDTO): Promise<ProductDTO> {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await basicAPI(postProductLink+addParam({employeeId}), "POST", JSON.stringify(product));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return response.json();
}

export async function putProductAPI(productId: string, product: PutProductDTO): Promise<ProductDTO> {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await basicAPI(putProductLink(productId)+addParam({employeeId}), "PUT", JSON.stringify(product));
  
  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return response.json();
}

export async function deleteProductAPI(productId: string) {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await basicAPI(deleteProductLink(productId)+addParam({employeeId}), "DELETE", null);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Product modifiers
export async function postProductModifierAPI(dto: PostProductModifierDTO): Promise<ProductModifierDTO> {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await basicAPI(postProductModifierLink+addParam({employeeId}), "POST", JSON.stringify(dto));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return response.json();
}

export async function getProductModifierAPI(pageNumber: number, pageSize: number, businessId: string): Promise<GetProductModifiersDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize };
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await fetch(getProductModifierListLink+addParam({businessId, employeeId, pageination}));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putProductModifierAPI(modifierId: string, dto: PutProductModifierDTO): Promise<ProductModifierDTO> {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await basicAPI(putProductModifierLink(modifierId)+addParam({employeeId}), "PUT", JSON.stringify(dto));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return response.json();
}

export async function deleteProductModifierAPI(modifierId: string) {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await basicAPI(deleteProductModifierLink(modifierId)+addParam({employeeId}), "DELETE", null);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Discount / Giftcard
export async function postDiscountAPI(dto: PostDiscountDTO): Promise<DiscountDTO> {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await basicAPI(postDiscountLink+addParam({employeeId}), "POST", JSON.stringify(dto));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getDiscountsAPI(pageNumber: number, pageSize: number, businessId: string): Promise<DiscountDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize };
  const response = await fetch(getDiscountsLink+addParam({pageination, businessId}));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getGiftcardsAPI(pageNumber: number, pageSize: number, businessId: string): Promise<DiscountDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize };
  const response = await fetch(getGiftcardsLink+addParam({pageination, businessId}));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putDiscountAPI(discountId: string, dto: PutDiscountDTO): Promise<DiscountDTO>{
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await basicAPI(putDiscountLink(discountId)+addParam({employeeId}), "PUT", JSON.stringify(dto));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function useDiscountAPI(discountId: string): Promise<DiscountDTO> {
  const response = await basicAPI(increaseDiscGiftUsageLink(discountId), "PUT", null);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function deleteDiscountAPI(discountId: string) {
  const employeeId = localStorage.getItem("userId") ?? undefined;
  const response = await basicAPI(deleteDiscountLink(discountId)+addParam({employeeId}), "DELETE", null);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Tax
export async function postTaxAPI(dto: PostTaxDTO): Promise<TaxDTO> {
  const response = await basicAPI(postTaxLink, "POST", JSON.stringify(dto));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getTaxesAPI(pageNumber: number, pageSize: number): Promise<GetTaxesDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize };
  const response = await fetch(getTaxLink+addParam({pageination}));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putTaxesAPI(taxId: string, dto: PutTaxDTO) {
  const response = await basicAPI(putTaxLink(taxId), "PUT", JSON.stringify(dto));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function deleteTaxAPI(taxId: string) {
  const response = await basicAPI(deleteTaxLink(taxId), "DELETE", null);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Service charges
export async function postServiceChargeAPI(dto: PostServiceChargeDTO): Promise<ServiceChargeDTO> {
  const response = await basicAPI(postServiceChargeLink, "POST", JSON.stringify(dto));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getServiceChargesAPI(pageNumber: number, pageSize: number): Promise<GetServiceChargeDTO> {
  const pageination = {
    pageNumber: pageNumber,
    pageSize: pageSize
  }
  const response = await fetch(getServiceChargeLink+addParam({pageination}));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putServiceChargeAPI(serviceChargeId: string, dto: PutServiceChargeDTO): Promise<ServiceChargeDTO> {
  const response = await basicAPI(putServiceChargeLink(serviceChargeId), "PUT", JSON.stringify(dto));
  
  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function deleteServiceCharge(serviceChargeId: string) {
  const response = await basicAPI(deleteServiceChargeLink(serviceChargeId), "DELETE", null);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Order
export async function postOrderAPI(dto: PostOrderDTO): Promise<OrderDTO> {
  const response = await basicAPI(postOrderLink, "POST", JSON.stringify(dto));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getOrdersAPI(pageNumber: number, pageSize: number): Promise<OrderDTO> {
  const pageination = {
    pageNumber: pageNumber+1,
    pageSize: pageSize
  };
  const response = await fetch(getOrdersLink+addParam({pageination}));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getOrderAPI(orderId: string): Promise<OrderDTO> {
  const response = await fetch(getOrderLink(orderId));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putOrderAPI(orderId: string, dto: PutOrderDTO): Promise<OrderDTO> {
  const response = await basicAPI(putOrderLink(orderId), "PUT", JSON.stringify(dto));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function getOrderReceiptAPI(id: string): Promise<OrderDTO> {
  const response = await fetch(getOrderReceiptLink(id));
  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}


//User
export async function getUserAPI(userId: string): Promise<UserDTO> {
  const response = await fetch(getUserLink(userId));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}


//Business
export async function getBusinessAPI(id: string): Promise<BusinessDTO> {
  const response = await fetch(getBusinessLink(id));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return response.json();
}

export async function putBusinessAPI(business: BusinessDTO): Promise<BusinessDTO> {
  const response = await basicAPI(putBusinessLink(business.id), "PUT", JSON.stringify(business));

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
    body: null
  })

  return response;
}