import React, { useState } from "react";

const MyProfile = () => {
  const [isEdit, setIsEdit] = useState(false);
  const [image, setImage] = useState(null);
  const [userData, setUserData] = useState({
    name: "Manojit Das",
    email: "johndoe@example.com",
    phone: "1234567890",
    address: { line1: "123 Street Name", line2: "City, Country" },
    gender: "Male",
    dob: "1990-01-01",
    image: "./Manojit.jpg",
    collegeName:'SETGOI',
    collegeRoll:'123455'
  });

  return (
    <div className="w-full flex flex-col gap-2 text-sm px-10 py-10 bg-darkbg">
      {isEdit ? (
        <label htmlFor="image">
          <div className="inline-block relative cursor-pointer">
            <img
              className="w-36 rounded opacity-70"
              src={image ? URL.createObjectURL(image) : userData.image}
              alt="Profile"
            />
            <img
              className="w-10 absolute bg-slate-300 bottom-12 right-12"
              src={image ? "" : "./upload_icon.png"}
              alt="Upload"
            />
          </div>
          <input
            onChange={(e) => setImage(e.target.files[0])}
            type="file"
            id="image"
            hidden
          />
        </label>
      ) : (
        <img className="w-36 rounded" src={userData.image} alt="Profile" />
      )}

      {isEdit ? (
        <input
          className="bg-gray-100 p-1 rounded-md text-3xl font-medium max-w-60 mt-4 "
          type="text"
          value={userData.name}
          onChange={(e) =>
            setUserData((prev) => ({ ...prev, name: e.target.value }))
          }
        />
      ) : (
        <p className="font-medium text-3xl text-whitee mt-4">{userData.name}</p>
      )}

      <hr className="bg-zinc-400 h-[1px] border-none" />

      {/* Contact Information */}
      <div>
        <p className="uppercase text-neutral-500 underline mt-3">
          Contact Information
        </p>
        <div className="grid grid-cols-[1fr_3fr] gap-y-2.5 mt-3 text-neutral-700">
          <p className="font-medium">Email id:</p>
          <p className="text-blue-400">{userData.email}</p>

          <p className="font-medium">Phone:</p>
          {isEdit ? (
            <input
              className="bg-gray-200 p-1 max-w-52 rounded-md"
              type="tel"
              value={userData.phone}
              onChange={(e) =>
                setUserData((prev) => ({ ...prev, phone: e.target.value }))
              }
            />
          ) : (
            <p className="text-blue-400">{userData.phone}</p>
          )}

          <p className="font-medium">Address:</p>
          {isEdit ? (
            <p>
              <input
                className="bg-gray-200 p-1 rounded-md"
                type="text"
                value={userData.address.line1}
                onChange={(e) =>
                  setUserData((prev) => ({
                    ...prev,
                    address: { ...prev.address, line1: e.target.value },
                  }))
                }
              />
              <br />
              <input
                className="bg-gray-200 mt-1 p-1 rounded-md"
                type="text"
                value={userData.address.line2}
                onChange={(e) =>
                  setUserData((prev) => ({
                    ...prev,
                    address: { ...prev.address, line2: e.target.value },
                  }))
                }
              />
            </p>
          ) : (
            <p className="text-gray-500">
              {userData.address.line1}
              <br />
              {userData.address.line2}
            </p>
          )}
        </div>
      </div>

      {/* Contact Information */}
      <div>
        <p className="uppercase text-neutral-500 underline">
          Basic Information
        </p>
        <div className="grid grid-cols-[1fr_3fr] text-neutral-700 gap-y-2.5 mt-3">
          <p className="font-medium">Gender:</p>
          {isEdit ? (
            <select
              className="max-w-20 bg-gray-200 p-1 rounded-md"
              onChange={(e) =>
                setUserData((prev) => ({ ...prev, gender: e.target.value }))
              }
              value={userData.gender}
            >
              <option value="Male">Male</option>
              <option value="Female">Female</option>
            </select>
          ) : (
            <p className="text-gray-500">{userData.gender}</p>
          )}

          <p className="font-medium">Birthday:</p>
          {isEdit ? (
            <input
              className="max-w-28 bg-gray-200 p-1 rounded-md"
              type="date"
              onChange={(e) =>
                setUserData((prev) => ({ ...prev, dob: e.target.value }))
              }
              value={userData.dob}
            />
          ) : (
            <p className="text-gray-500">
              {userData.dob.split("-").reverse().join("-")}
            </p>
          )}
        </div>
      </div>

      {/* College Information */}
      <div>
        <p className="uppercase text-neutral-500 underline mt-3">
          College Information
        </p>
        <div className="grid grid-cols-[1fr_3fr] gap-y-2.5 mt-3 text-neutral-700">
          <p className="font-medium">College Name:</p>
          {isEdit ? (
            <input
              className="bg-gray-200 p-1 max-w-52 rounded-md"
              type="text"
              value={userData.collegeName}
              onChange={(e) =>
                setUserData((prev) => ({
                  ...prev,
                  collegeName: e.target.value,
                }))
              }
            />
          ) : (
            <p className="text-blue-400">{userData.collegeName}</p>
          )}

          <p className="font-medium">College Roll Number:</p>
          {isEdit ? (
            <input
              className="bg-gray-200 p-1 max-w-52 rounded-md"
              type="text"
              value={userData.collegeRoll}
              onChange={(e) =>
                setUserData((prev) => ({
                  ...prev,
                  collegeRoll: e.target.value,
                }))
              }
            />
          ) : (
            <p className="text-gray-500">{userData.collegeRoll}</p>
          )}
        </div>
      </div>

      <div className="mt-10">
        {isEdit ? (
          <button
            className="border border-primary px-8 py-2 rounded-full hover:bg-primary hover:text-white transition-all duration-300"
            onClick={() => setIsEdit(false)}
          >
            Save Information
          </button>
        ) : (
          <button
            className="border border-primary px-8 py-2 rounded-full hover:bg-primary hover:text-white text-whitee transition-all duration-300"
            onClick={() => setIsEdit(true)}
          >
            Edit
          </button>
        )}
      </div>
    </div>
  );
};

export default MyProfile;
