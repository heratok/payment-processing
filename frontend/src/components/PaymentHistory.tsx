import React, { useEffect, useState } from 'react';
import { paymentService, PaymentResponse } from '../services/api';
import { motion } from 'framer-motion';

export const PaymentHistory: React.FC = () => {
  const [payments, setPayments] = useState<PaymentResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchPayments = async () => {
      try {
        const data = await paymentService.getPaymentHistory();
        setPayments(data);
      } catch (err) {
        setError('Error al cargar el historial de pagos');
      } finally {
        setLoading(false);
      }
    };

    fetchPayments();
  }, []);

  const getPaymentMethodLabel = (method: string) => {
    switch (method) {
      case 'creditcard':
        return 'Tarjeta de Crédito';
      case 'debitcard':
        return 'Tarjeta de Débito';
      case 'paypal':
        return 'PayPal';
      default:
        return method;
    }
  };

  if (loading) {
    return (
      <motion.div 
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        className="flex justify-center items-center h-64"
      >
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600"></div>
      </motion.div>
    );
  }

  if (error) {
    return (
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        className="rounded-lg bg-red-50 p-4"
      >
        <div className="text-sm text-red-700">{error}</div>
      </motion.div>
    );
  }

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      className="space-y-6"
    >
      <h2 className="text-2xl font-bold text-gray-800">Historial de Pagos</h2>
      
      {payments.length === 0 ? (
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="text-center text-gray-500 py-8"
        >
          No hay pagos registrados
        </motion.div>
      ) : (
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  ID de Transacción
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Método de Pago
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Estado
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Mensaje
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {payments.map((payment, index) => (
                <motion.tr
                  key={payment.transactionId}
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ delay: index * 0.1 }}
                  className="hover:bg-gray-50"
                >
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {payment.transactionId}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {getPaymentMethodLabel(payment.paymentMethod)}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <motion.span
                      whileHover={{ scale: 1.05 }}
                      className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                        payment.success 
                          ? 'bg-green-100 text-green-800' 
                          : 'bg-red-100 text-red-800'
                      }`}
                    >
                      {payment.success ? 'Exitoso' : 'Fallido'}
                    </motion.span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    {payment.message}
                  </td>
                </motion.tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </motion.div>
  );
}; 