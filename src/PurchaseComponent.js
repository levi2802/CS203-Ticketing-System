const PurchaseComponent = ({ userId, movieId, seats, totalPrice }) => {
  
  const handleConfirmPurchase = async () => {
    try {
      const orderData = {
        // id: generateUniqueId(), // function to generate a unique ID for the order
        user: { id: userId }, // user ID passed as a prop
        purchaseDateTime: new Date().toISOString(), // current date and time
        movieName, // assuming movieName is a string representing the name of the movie
        seats, // array of seat IDs (as strings)
        totalPrice, // total price passed as a prop
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
