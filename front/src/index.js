import React from 'react';
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './css/index.css';
import Game from "./js/page/game";
import Username from "./js/page/username";
import Search from "./js/page/search";
import Lobby from "./js/page/lobby";

ReactDOM.render(
    <Lobby />,
  document.getElementById('root')
);
