import { createContext, useContext } from "react";

export const AllContext = createContext();

export const AllProvider = ({ children }) => {

  //   Format Date
  const formatDate = (isoDateString) => {
    const date = new Date(isoDateString);
    const options = { day: "numeric", month: "long", year: "numeric" };
    return new Intl.DateTimeFormat("en-GB", options).format(date);
  };

  const value = {
    formatDate,
  };

  return <AllContext.Provider value={value}>{children}</AllContext.Provider>;
};

export const useAllContext = () => useContext(AllContext);
