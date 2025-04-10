import React from "react";

const badgeLevels = [
  {
    name: "Bronze Badge",
    description: "Earned after 10 uploads. You're just getting started!",
    image: "/badge/bronze.png",
    min: 10,
  },
  {
    name: "Silver Badge",
    description: "Unlocked at 50 uploads. Keep going, you're leveling up!",
    image: "/badge/silver.png",
    min: 50,
  },
  {
    name: "Gold Badge",
    description:
      "Achieved at 200 uploads. Your contributions are making an impact!",
    image: "/badge/gold.png",
    min: 200,
  },
  {
    name: "Ruby Badge",
    description: "Secured at 500 uploads. You're among the top contributors!",
    image: "/badge/ruby.png",
    min: 500,
  },
  {
    name: "Emerald Badge",
    description: "Unlocked at 1000 uploads. You’ve reached legendary status!",
    image: "/badge/emerald.png",
    min: 1000,
  },
];

export default function BadgeProgress({ uploadCount }) {
  const currentBadge =
    [...badgeLevels].reverse().find((badge) => uploadCount >= badge.min) ||
    null;

  return (
    <div className="bg-[#0c1b2a] text-white p-6 rounded-xl  shadow-xl ">
      <h2 className="text-lg font-semibold mb-2 text-center">
        Upload notes, projects & past papers
      </h2>
      <p className="text-sm text-center mb-4">
        –Win badges & flex like a champ!
      </p>
      <div className="border-t border-gray-600 mb-4" />

      {badgeLevels.map((badge, index) => {
        const isCurrent = currentBadge && badge.name === currentBadge.name;

        return (
          <div key={index} className="flex items-start mb-5">
            <div
              className={`w-30 h-30 flex flex-col items-center justify-center  overflow-hidden mr-4  ${
                isCurrent ? "opacity-100" : "opacity-50"
              }`}
            >
              <img
                src={badge.image}
                alt={badge.name}
                className="w-full h-full object-contain"
              />
              {isCurrent && (
                <span className="text-[10px] text-white underline decoration-2 decoration-white ">
                  At Present
                </span>
              )}
            </div>
            <div>
              <h3
                className={`font-semibold text-sm text-center ${
                  isCurrent ? "underline decoration-2 decoration-white" : ""
                } `}
              >
                {badge.name}
              </h3>
              <p className="text-xs text-gray-300 text-center">
                {badge.description}
              </p>
            </div>
          </div>
        );
      })}

      <h2 className="text-center text-2xl font-bold mt-6 underline">Badges</h2>
    </div>
  );
}
