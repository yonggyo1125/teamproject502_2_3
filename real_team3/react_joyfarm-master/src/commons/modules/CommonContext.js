import React, { useState, createContext } from 'react';

const CommonContext = createContext({
  states: {
    linkText: '',
    linkHref: '',
    subTitle: '',
  },
  actions: {
    setLinkText: null,
    setLinkHref: null,
    setSubTitle: null,
  },
});

const CommonProvider = ({ children }) => {
  const [linkText, setLinkText] = useState('');
  const [linkHref, setLinkHref] = useState('');
  const [subTitle, setSubTitle] = useState('');
  const value = {
    states: { linkText, linkHref, subTitle },
    actions: { setLinkText, setLinkHref, setSubTitle },
  };

  return (
    <CommonContext.Provider value={value}>{children}</CommonContext.Provider>
  );
};

const { Consumer: CommonConsumer } = CommonContext;

export { CommonProvider, CommonConsumer };

export default CommonContext;
