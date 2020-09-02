import React from "react";
import Board from "../directive/board";
import Chip from "../directive/chip";

class Game extends React.Component {

    render() {
        return (
            <div className="w-100 h-100">
                <Board />
                <Chip/>
            </div>
        )
    }
}

export default Game;