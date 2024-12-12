import { BusinessDTO, UserDTO } from "./Responses";
import { getBusinessLink, getUserLink, postBusinessLink, putBusinessLink } from "./Routes";



export async function getUserAPI(id: string): Promise<UserDTO> {
  const response = await fetch(getUserLink(id));

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }

  const data: UserDTO = await response.json();
  return data;
}

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

async function basicAPI(url: string, method: string, body: string) {
  const response = await fetch(url, {
    method: method,
    headers: {
      "Content-Type": "application/json",
    },
    body: body
  })

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.text}`);
  }
  const data = await response;
  return data;
}