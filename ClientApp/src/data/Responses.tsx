export interface UserDTO {
  id: string;
  fullName: string;
  phoneNumber: string;
  emailAddress: string;
  businessId: string;
  role: string;
}

export interface BusinessDTO {
  id: string;
  ownerId: string;
  name: string;
  phoneNumber: string;
  emailAddress: string;
  address: string;
}