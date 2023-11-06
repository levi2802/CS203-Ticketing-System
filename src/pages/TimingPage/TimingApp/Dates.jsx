import React from 'react';

function Dates({ selectedDate, setSelectedDate }) {
  // const [selectedDateInfo, setSelectedDateInfo] = useState(null);
  const today = new Date();
  const fiveDaysFromToday = new Date(today);
  fiveDaysFromToday.setDate(today.getDate() + 5); // Calculate the date 5 days from today

  const minDate = today.toISOString().split('T')[0];
  const maxDate = fiveDaysFromToday.toISOString().split('T')[0];
  const timingData = [
      ['10:30', '11:30','02:00'],
      ['09:00', '22:00'],
      ['11:00', '12:30', '14:45', '17:00'],
  ];

  // Get the current time
  const currentTime = new Date();

  // Function to check if a time string is before the current time
  function isBeforeCurrentTime(timeStr) {
      const timeParts = timeStr.split(':');
      const hours = parseInt(timeParts[0], 10);
      const minutes = parseInt(timeParts[1], 10);

      // Create a Date object for the input time
      const timeDate = new Date();
      timeDate.setHours(hours, minutes, 0, 0);

      return timeDate < currentTime;
  }

  let filteredData = timingData;

  const handleDateChange = (e) => {
    const selected = new Date(e.target.value);
    setSelectedDate(selected.toISOString().split('T')[0]);
    const dateInfo = {
      day: selected.getDate(),
      month: selected.getMonth() + 1,
      year: selected.getFullYear(),
    };
    // setSelectedDateInfo(dateInfo);

    if(currentTime.getDate() === Number(dateInfo.day)){
      filteredData = timingData.map(times => times.filter(time => !isBeforeCurrentTime(time)));
    }
    const filteredDataString = JSON.stringify(filteredData);
    localStorage.setItem('filteredData', filteredDataString);
    
    // Store selected date info in local storage
    const formattedDate = "" + dateInfo.month + '/' + dateInfo.day + '/' + dateInfo.year;
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
