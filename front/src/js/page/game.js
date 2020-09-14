import React from "react";
import Table from "../directive/table";
import Chip from "../directive/chip";

class Game extends React.Component {

    render() {
        return (
            <div className="w-100 h-100">
                <Table />
            </div>
        )
    }
}

export default Game;