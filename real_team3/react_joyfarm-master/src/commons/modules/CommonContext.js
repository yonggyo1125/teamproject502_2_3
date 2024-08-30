import React, { useState, createContext } from 'react';

const CommonContext = createContext({
  states: {
    linkText: '',
    linkHref: '',
  },
  actions: {
    setLinkText: null,
    setLinkHref: null,
  },
});

const CommonProvider = ({ children }) => {
  const [linkText, setLinkText] = useState('');
  const [linkHref, setLinkHref] = useState('');

  const value = {
    states: { linkText, linkHref },
    actions: { setLinkText, setLinkHref },
  };

  return (
    <CommonContext.Provider value={value}>{children}</CommonContext.Provider>
  );
};

const { Consumer: CommonConsumer } = CommonContext;

export { CommonProvider, CommonConsumer };

export default CommonContext;
