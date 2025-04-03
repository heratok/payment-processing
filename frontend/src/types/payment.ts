export interface Payment {
  id: string;
  amount: number;
  currency: string;
  status: 'PENDING' | 'COMPLETED' | 'FAILED' | 'CANCELLED';
  description: string;
  createdAt: string;
  updatedAt: string;
}

export interface PaymentRequest {
  amount: number;
  currency: string;
  description: string;
  cardNumber: string;
  cardHolder: string;
  expiryDate: string;
  cvv: string;
} 