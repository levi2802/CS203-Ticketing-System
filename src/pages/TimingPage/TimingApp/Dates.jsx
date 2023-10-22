import React from 'react';

function Dates({ selectedDate, setSelectedDate }) {
  const today = new Date();
  const fiveDaysFromToday = new Date(today);
  fiveDaysFromToday.setDate(today.getDate() + 5); // Calculate the date 5 days from today

  const minDate = today.toISOString().split('T')[0];
  const maxDate = fiveDaysFromToday.toISOString().split('T')[0];

  const handleDateChange = (e) => {
    setSelectedDate(e.target.value);
  };

  const handleDateKeyDown = (e) => {
    if (e.key === 'Enter') {
      const selected = new Date(selectedDate);
      if (selected < new Date(minDate)) {
        setSelectedDate(minDate);
      } else if (selected > new Date(maxDate)) {
        setSelectedDate(maxDate);
      }
    }
  };

  return (
    <div>
      <label htmlFor="datePicker">Select Date:</label>
      <input
        type="date"
        id="datePicker"
        value={selectedDate}
        onChange={handleDateChange}
        onKeyDown={handleDateKeyDown}
        min={minDate}
        max={maxDate}
      />
    </div>
  );
}

export default Dates;
