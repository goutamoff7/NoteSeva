import { badgeLevels } from "../../data/data.js"; 

export default function BadgeProgress({userUploads}) {
  const uploadCount = userUploads.organizerDTOList.length + userUploads.pyqDTOList.length + userUploads.notesDTOList.length;
  
  const currentBadge =
    [...badgeLevels].reverse().find((badge) => uploadCount >= badge.min) ||
    null;
  
  return (
    <div className="bg-[#0c1b2a] text-white p-6 rounded-xl shadow-xl">
      <h2 className="text-lg font-semibold mb-2 text-center">
        Upload notes, projects & past papers
      </h2>
      <p className="text-sm text-center mb-4">
        –Win badges & flex like a champ!
      </p>
      <div className="border-t border-gray-600 mb-4" />

      {/* Badge List */}
      {badgeLevels.map((badge, index) => {
        const isUnlocked = uploadCount >= badge.min;
        const isCurrent = currentBadge && badge.name === currentBadge.name;

        return (
          <div key={index} className="flex items-center mb-5">
            <div
              className={`w-30 h-30 flex flex-col items-center justify-center overflow-hidden mr-4 ${
                isUnlocked ? "opacity-100" : "opacity-30"
              }`}
            >
              <img
                src={badge.image}
                alt={badge.name}
                className="w-full h-full object-contain"
              />
              {isCurrent && (
                <span className="text-[10px] text-white underline decoration-2 decoration-white">
                  At Present
                </span>
              )}
            </div>
            <div className={` ${isUnlocked ? "opacity-100" : "opacity-30"}`}>
              <h3
                className={`font-semibold text-sm text-center ${
                  isUnlocked ? "underline decoration-2 decoration-white" : ""
                }`}
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
