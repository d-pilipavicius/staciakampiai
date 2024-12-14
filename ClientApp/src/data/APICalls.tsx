import { BusinessDTO, GetProductModifiersDTO, GetProductsDTO, PostProductDTO, PostProductModifierDTO, ProductDTO, ProductModifierDTO, PutProductDTO, PutProductModifierDTO, UserDTO } from "./Responses";
import { deleteProductLink, getBusinessLink, getProductListLink, getProductModifierListLink, getUserLink, postProductLink, postProductModifierLink, putBusinessLink, putProductLink, putProductModifierLink } from "./Routes";

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
  const response = await fetch(getProductListLink+`?pageNumber=${pageNumber-1}&pageSize=${pageSize}&businessId=${businessId}&employeeId=${localStorage.getItem("userId")}`);

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
  const response = await basicAPI(postProductLink+`?employeeId=${localStorage.getItem("userId")}`, "POST", JSON.stringify(product));

  return await response.json();
}

export async function putProductAPI(productId: string, product: PutProductDTO): Promise<ProductDTO> {
  const response = await basicAPI(putProductLink(productId)+`?employeeId=${localStorage.getItem("userId")}`, "PUT", JSON.stringify(product));
  return await response.json();
}

export async function deleteProductAPI(productId: string) {
  const response = await basicAPI(deleteProductLink(productId)+`?employeeId=${localStorage.getItem("userId")}`, "DELETE", "");

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//Product modifiers
export async function postProductModifierAPI(dto: PostProductModifierDTO): Promise<ProductModifierDTO> {
  const response = await basicAPI(postProductModifierLink+`?employeeId=${localStorage.getItem("userId")}`, "POST", JSON.stringify(dto));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return await response.json();
}

export async function getProductModifierAPI(pageNumber: number, pageSize: number, businessId: string): Promise<GetProductModifiersDTO> {
  const response = await fetch(getProductModifierListLink+`?pageNumber=${pageNumber}&pageSize=${pageSize}&businessId=${businessId}&employeeId=${localStorage.getItem("userId")}`);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  return await response.json();
}

export async function putProductModifierAPI(modifierId: string, dto: PutProductModifierDTO): Promise<ProductModifierDTO> {
  const response = await basicAPI(putProductModifierLink(modifierId)+`?employeeId=${localStorage.getItem("userId")}`, "PUT", JSON.stringify(dto));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  
  return await response.json();
}

export async function deleteProductModifierAPI(modifierId: string) {
  const response = await basicAPI(deleteProductModifierAPI(modifierId)+`?employeeId=${localStorage.getItem("userId")}`, "DELETE", "");

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
}

//User
export async function getUserAPI(id: string): Promise<UserDTO> {
  const response = await fetch(getUserLink(id));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  const data: UserDTO = await response.json();
  return data;
}


//Business
export async function getBusinessAPI(id: string): Promise<BusinessDTO> {
  const response = await fetch(getBusinessLink(id));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  const data: BusinessDTO = await response.json();
  return data;
}

export async function putBusinessAPI(business: BusinessDTO): Promise<BusinessDTO> {
  const link = putBusinessLink(business.id);
  const response = await basicAPI(link, "PUT", JSON.stringify(business));

  const data: BusinessDTO = await response.json();
  return data;
}


//Custom API sending method
async function basicAPI(url: string, method: string, body: string) {
  const response = await fetch(url, {
    method: method,
    headers: {
      "Content-Type": "application/json",
    },
    body: body
  })

  return response;
}