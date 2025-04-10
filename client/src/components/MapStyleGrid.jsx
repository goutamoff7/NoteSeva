import React from "react";

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

// Example data format: { date: "2025-03-01", count: 3 }
const generateDummyData = () => {
  const data = [];
  const now = new Date();
  for (let i = 0; i < 365; i++) {
    const date = new Date(now);
    date.setDate(date.getDate() - i);
    data.push({
      date: date.toISOString().split("T")[0],
      count: Math.floor(Math.random() * 6),
    });
  }
  return data.reverse();
};

const getColor = (count) => {
  if (count === 0) return "bg-[#0c1b2a] border border-gray-700";
  if (count < 2) return "bg-green-900";
  if (count < 4) return "bg-green-600";
  return "bg-green-400";
};

const MapStyleGrid = () => {
  const data = generateDummyData();

  // Group by weeks
  const weeks = [];
  let currentWeek = [];
  let weekDay = new Date(data[0].date).getDay();

  // Fill the first week with blanks if not starting on Sunday
  for (let i = 0; i < weekDay; i++) {
    currentWeek.push(null);
  }

  data.forEach((day) => {
    currentWeek.push(day);
    if (currentWeek.length === 7) {
      weeks.push(currentWeek);
      currentWeek = [];
    }
  });

  if (currentWeek.length > 0) {
    while (currentWeek.length < 7) {
      currentWeek.push(null);
    }
    weeks.push(currentWeek);
  }

  return (
    <div className="bg-[#0c1b2a] text-white p-4 rounded-xl w-auto shadow-xl my-2 border border-[#4b5563]">
      <p className="mb-2 text-sm">Daily Upload Map</p>
      <div className="overflow-x-auto">
        <div className="flex">
          {weeks.map((week, weekIndex) => (
            <div key={weekIndex} className="flex flex-col mr-1">
              {week.map((day, dayIndex) => (
                <div
                  key={dayIndex}
                  title={day ? `${day.date} - ${day.count} uploads` : "No data"}
                  className={`w-3 h-3 rounded-full mb-[2px] ${
                    day ? getColor(day.count) : "bg-transparent"
                  }`}
                ></div>
              ))}
            </div>
          ))}
        </div>
        <div className="flex justify-end mt-2 text-xs text-gray-400">
          <span className="mr-2">Less</span>
          <div className="w-4 h-4 rounded-full bg-[#0c1b2a] border border-gray-700 mx-1"></div>
          <div className="w-4 h-4 rounded-full bg-green-900 mx-1"></div>
          <div className="w-4 h-4 rounded-full bg-green-600 mx-1"></div>
          <div className="w-4 h-4 rounded-full bg-green-400 mx-1"></div>
          <span className="ml-2">More</span>
        </div>
      </div>
    </div>
  );
};

export default MapStyleGrid;
