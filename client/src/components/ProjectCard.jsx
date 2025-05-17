import { Link } from "react-router-dom";
import StarRating from "./StarRatting";
import VerifiedBadge from "./VarifiedBadge";

const ProjectCard = ({ id, title, para, image, userName, rating, isVerified }) => {
  return (
    <Link to={`/project/${id}`}>
      <div className="bg-whitee p-5 flex flex-col justify-center gap-4 rounded-3xl w-[350px] h-[350px]">
        <div className="rounded-2xl bg-blue-300 object-cover overflow-hidden">
          <img src={image} alt="project_image" className="w-full h-full" />
        </div>
        <div className="flex flex-col gap-2">
          <h1 className="uppercase font-semibold text-xl text-darkblue">
            {title}
          </h1>
          <p className="text-text_blue font-normal text-sm ">{para}</p>
          <span className="uppercase font-medium text-xl text-right">
            {userName}
          </span>
        </div>
        <div className="flex flex-row items-center justify-between">
          <StarRating rating={rating} />
          <VerifiedBadge isVerified={isVerified} />
        </div>
      </div>
    </Link>
  );
};

export default ProjectCard;
