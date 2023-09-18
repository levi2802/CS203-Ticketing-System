const PurchaseComponent = ({ userId, movieId, seats, totalPrice }) => {
  
  const handleConfirmPurchase = async () => {
    try {
      const orderData = {
        user: { id: userId }, // User ID passed as a prop
        movie: { id: movieId }, // Movie ID passed as a prop
        seats, // Seat IDs passed as a prop
        purchaseDateTime: new Date().toISOString(), // Current date and time
        totalPrice, // Total price passed as a prop
      };
      const response = await createPurchase(userId, orderData);
      console.log('Order created successfully: ', response);
      // Navigate to a confirmation page or show a success message
    } catch (error) {
      console.error('Error creating order: ', error);
      // Show an error message to the user
    }
  };

  return (
    <div>
      <button onClick={handleConfirmPurchase}>Confirm Purchase</button>
    </div>
  );
};

export default PurchaseComponent;
