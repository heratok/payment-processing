import React, { useEffect, useState } from 'react';
import { paymentService, PaymentResponse } from '../services/api';
import { motion } from 'framer-motion';
import { useTheme } from '../theme/ThemeContext';

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
        className="flex items-center justify-center h-64"
      >
        <div className="w-12 h-12 border-4 border-primary border-t-transparent rounded-full animate-spin"></div>
      </motion.div>
    );
  }

  if (error) {
    return (
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        className="p-4 rounded-lg bg-destructive/10 text-destructive border border-destructive/20"
      >
        <div className="text-sm">{error}</div>
      </motion.div>
    );
  }

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      className="space-y-6"
    >
      <h2 className="text-2xl font-bold text-foreground">
        Historial de Pagos
      </h2>
      
      {payments.length === 0 ? (
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="py-8 text-center text-muted-foreground"
        >
          No hay pagos registrados
        </motion.div>
      ) : (
        <div className="relative overflow-hidden rounded-lg border bg-card">
          <div className="overflow-x-auto">
            <table className="w-full border-collapse text-sm">
              <thead>
                <tr className="border-b bg-muted/50 transition-colors">
                  <th className="h-12 px-4 text-left align-middle font-medium text-muted-foreground">
                    ID de Transacción
                  </th>
                  <th className="h-12 px-4 text-left align-middle font-medium text-muted-foreground">
                    Método de Pago
                  </th>
                  <th className="h-12 px-4 text-left align-middle font-medium text-muted-foreground">
                    Estado
                  </th>
                  <th className="h-12 px-4 text-left align-middle font-medium text-muted-foreground">
                    Mensaje
                  </th>
                </tr>
              </thead>
              <tbody className="divide-y divide-border">
                {payments.map((payment) => (
                  <tr
                    key={payment.transactionId}
                    className="transition-colors hover:bg-muted/50"
                  >
                    <td className="p-4 align-middle text-sm text-foreground">
                      {payment.transactionId}
                    </td>
                    <td className="p-4 align-middle text-sm text-foreground">
                      {getPaymentMethodLabel(payment.paymentMethod)}
                    </td>
                    <td className="p-4 align-middle">
                      <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${
                        payment.success
                          ? 'bg-green-500/10 text-green-500'
                          : 'bg-destructive/10 text-destructive'
                      }`}>
                        {payment.success ? 'Exitoso' : 'Fallido'}
                      </span>
                    </td>
                    <td className="p-4 align-middle text-sm text-foreground">
                      {payment.message}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </motion.div>
  );
}; 