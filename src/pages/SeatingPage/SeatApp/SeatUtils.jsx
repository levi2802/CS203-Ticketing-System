export const handleSeatSelect = (seats, row, col) => {
    //object of the seat that i clicked
    //const selectedSeat = seats.find(seat => seat.row === row && seat.num === col);
    const chosenSeats = seats.filter(seat => seat.selected).map(seat => seat.num + 1);

    //A list of selected seat numbers
    const selectedSeatNumbers = [];
    seats.forEach(seat => {
      if (seat.row === row && seat.selected) {
        selectedSeatNumbers.push(seat.num);
      }
    });
  
    const updatedSeats = seats.map(seat => {
      if (seat.row === row && seat.num === col) {
        if (selectedSeatNumbers.includes(col)) {
          if (selectedSeatNumbers.length > 1) {
            const maxSelected = Math.max(...selectedSeatNumbers);
            const minSelected = Math.min(...selectedSeatNumbers);
            if (col === maxSelected || col === minSelected) {
              seat.selected = !seat.selected;
            } else {
              alert("Please choose a seat with no space between.");
            }
          } else {
            seat.selected = !seat.selected;
          }
        } else if (
          selectedSeatNumbers.length === 0 ||
          selectedSeatNumbers.includes(col - 1) ||
          selectedSeatNumbers.includes(col + 1) ||
          selectedSeatNumbers.includes(col)
        ) {
          if (chosenSeats.length < 10) {
            seat.selected = !seat.selected;
          } else {
            alert("You cannot book more than 10 seats.");
          }
        } else {
          alert("Please choose a seat with no space between.");
        }
      }
      return seat;
    });
  
    return updatedSeats;
};

export const generateSeats = (Row, Col) => {
  let seats = [];

  //How many seats to create r=row i=col
  for (let r = 0; r < Row; r++) {
    for (let i = 0; i < Col; i++) {
      seats.push({ row: r, num: i, avail: true });
    }
  }
  return seats;
};

export const makeUnavail = (seats, unavailSeats) => {
  for (let i = 0; i < seats.length; i++) {
    for (let j = 0; j < unavailSeats.length; j++) {
      let r = unavailSeats[j][0];
      let c = unavailSeats[j][1];
      if (seats[i].row === r && seats[i].num === c) {
        seats[i].avail = false;
      }
    }
  }
};

export const nullArray = (r, c) => {
  let seatsGrid = [];
  for (let i = 0; i < r; i++) {
    let row = [];
    for (let j = 0; j < c; j++) {
      row.push(null);
    }
    seatsGrid.push(row);
  }
  return seatsGrid;
};

export const charConverter = (seatsGrid) => {
  const rowName = [];
  for (let i = 0; i < seatsGrid.length; i++) {
    let temp = 'A'.charCodeAt(0) + i;
    rowName.push(String.fromCharCode(temp));
  }
  return rowName;
};

export const dateConvertor = (currDate) =>{
  const date = new Date(currDate);
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  const formattedDate = date.toLocaleString('en-GB', options);
  return formattedDate;
}
  