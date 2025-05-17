import React from "react";

// Days and Months Arrays
const days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
const months = [
  "Jan",
  "Feb",
  "Mar",
  "Apr",
  "May",
  "Jun",
  "Jul",
  "Aug",
  "Sep",
  "Oct",
  "Nov",
  "Dec",
];

// Example data
const testData = [
  { date: "2025-03-01", uploads: ["note"] },
  { date: "2025-03-02", uploads: ["organizer"] },
  { date: "2025-05-03", uploads: ["pyq"] },
  { date: "2025-05-04", uploads: ["note", "organizer"] },
  { date: "2025-05-05", uploads: [] },
];

// Generate 365 days of data
const generateFullYearData = () => {
  const dataMap = {};
  testData.forEach((item) => {
    dataMap[item.date] = item.uploads;
  });

  const data = [];
  const now = new Date();
  for (let i = 0; i < 365; i++) {
    const date = new Date(now);
    date.setDate(date.getDate() - i);
    const iso = date.toISOString().split("T")[0];
    data.push({
      date: iso,
      uploads: dataMap[iso] || [],
    });
  }
  return data.reverse(); // Chronological order
};

// Color logic
const getColor = (uploads) => {
  const count = uploads.length;
  if (count === 0) return "bg-[#0c1b2a] border border-gray-700";
  if (count === 1) return "bg-green-900";
  if (count === 2) return "bg-green-600";
  return "bg-green-400";
};

const CalenderStyleGrid = () => {
  const data = generateFullYearData();

  // Group by weeks
  const weeks = [];
  let currentWeek = [];
  const firstDayIndex = new Date(data[0].date).getDay();

  for (let i = 0; i < firstDayIndex; i++) currentWeek.push(null);

  data.forEach((day) => {
    currentWeek.push(day);
    if (currentWeek.length === 7) {
      weeks.push(currentWeek);
      currentWeek = [];
    }
  });

  if (currentWeek.length > 0) {
    while (currentWeek.length < 7) currentWeek.push(null);
    weeks.push(currentWeek);
  }

  return (
    <div className="bg-[#0c1b2a] text-white p-4 rounded-xl w-full shadow-xl my-4 border border-[#4b5563] overflow-x-auto">
      <p className="mb-3 text-sm font-medium">Daily Upload Map</p>

      {/* Month Labels */}
      <div className="flex ml-[20px] mb-2">
        {weeks.map((week, weekIndex) => {
          const firstDay = week.find((d) => d !== null);
          const prevFirstDay = weeks[weekIndex - 1]?.find((d) => d !== null);
          const currentMonth = firstDay
            ? new Date(firstDay.date).getMonth()
            : null;
          const prevMonth = prevFirstDay
            ? new Date(prevFirstDay.date).getMonth()
            : null;

          const showMonth = currentMonth !== prevMonth;

          return (
            <div
              key={weekIndex}
              className={`w-4 text-xs text-gray-400 text-center ${
                showMonth ? "ml-0" : ""
              }`}
            >
              {showMonth ? months[currentMonth] : ""}
            </div>
          );
        })}
      </div>

      <div className="flex">
        {/* Weekday Labels (Left Column) */}
        <div className="flex flex-col mr-2 ">
          {days.map((dayName, i) => (
            <div key={i} className="h-3 mb-[6px] text-sm  text-gray-400">
              {dayName}
            </div>
          ))}
        </div>

        {/* Grid Columns */}
        {weeks.map((week, weekIndex) => {
          const firstDay = week.find((d) => d !== null);
          const prevFirstDay = weeks[weekIndex - 1]?.find((d) => d !== null);
          const currentMonth = firstDay
            ? new Date(firstDay.date).getMonth()
            : null;
          const prevMonth = prevFirstDay
            ? new Date(prevFirstDay.date).getMonth()
            : null;

          const isNewMonth = currentMonth !== prevMonth;

          return (
            <div
              key={weekIndex}
              className={`flex flex-col items-center ${
                isNewMonth ? "ml-2" : "mr-[2px]"
              }`}
            >
              {week.map((day, dayIndex) => (
                <div
                  key={dayIndex}
                  title={
                    day
                      ? `${day.date} - ${
                          day.uploads.length
                        } upload(s): ${day.uploads.join(", ")}`
                      : "No data"
                  }
                  className={`w-3 h-3 rounded-full mb-[2px] cursor-pointer ${
                    day ? getColor(day.uploads) : "bg-transparent"
                  }`}
                ></div>
              ))}
            </div>
          );
        })}
      </div>

      {/* Legend */}
      <div className="flex justify-end mt-4 text-xs text-gray-400 items-center">
        <span className="mr-2">Less</span>
        <div className="w-4 h-4 rounded-full bg-[#0c1b2a] border border-gray-700 mx-1"></div>
        <div className="w-4 h-4 rounded-full bg-green-900 mx-1"></div>
        <div className="w-4 h-4 rounded-full bg-green-600 mx-1"></div>
        <div className="w-4 h-4 rounded-full bg-green-400 mx-1"></div>
        <span className="ml-2">More</span>
      </div>
    </div>
  );
};

export default CalenderStyleGrid;