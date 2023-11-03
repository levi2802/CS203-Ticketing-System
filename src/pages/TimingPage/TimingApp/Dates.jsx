import React, {useState} from 'react';

function Dates({ selectedDate, setSelectedDate }) {
  const [setSelectedDateInfo] = useState(null);
  const today = new Date();
  const fiveDaysFromToday = new Date(today);
  fiveDaysFromToday.setDate(today.getDate() + 5); // Calculate the date 5 days from today

  const minDate = today.toISOString().split('T')[0];
  const maxDate = fiveDaysFromToday.toISOString().split('T')[0];

  const handleDateChange = (e) => {
    const selected = new Date(e.target.value);
    setSelectedDate(selected.toISOString().split('T')[0]);
    const dateInfo = {
      day: selected.getDate(),
      month: selected.getMonth() + 1,
      year: selected.getFullYear(),
    };
    setSelectedDateInfo(dateInfo);
    
    // Store selected date info in local storage
    const formattedDate = `${dateInfo.day}/${dateInfo.month}/${dateInfo.year}`;
    localStorage.setItem('selectedDate', formattedDate);
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
