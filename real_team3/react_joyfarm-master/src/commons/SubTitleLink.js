import React, { useContext, useEffect } from 'react';
import CommonContext from './modules/CommonContext';

const SubTitleLink = ({ text, href }) => {
  const {
    actions: { setLinkText, setLinkHref },
  } = useContext(CommonContext);

  useEffect(() => {
    setLinkText(text);
    setLinkHref(href);
  }, [setLinkText, setLinkHref, text, href]);

  return <></>;
};

export default React.memo(SubTitleLink);
