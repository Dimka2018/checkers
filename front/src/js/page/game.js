import React from "react";
import Table from "../directive/table";
import MessageWindow from "../directive/message_window";
import GameMenu from "../directive/game_menu";

class Game extends React.Component {

    constructor(props) {
        super(props);
        this.controller = props.controller;
        this.state = {
            modalMode: false
        };
        this.modal = React.createRef();
        this.table = React.createRef();
        this.menu = React.createRef();
    }

    setMessage(message) {
        this.setState({message: message})
    }

    componentDidMount() {
        this.controller.subscribe('game', 'TABLE_CHANGED', message => {
            this.table.current.setBoardState(message.cells);
        });
        this.controller.subscribe('game', 'YOUR_TURN_STARTED', message => {
            this.menu.current.setYourTurn();
            this.table.current.unBlock();
        });
        this.controller.subscribe('game', 'ENEMY_TURN_STARTED', message => {
            this.menu.current.setEnemyTurn();
            this.table.current.block();
        });
        this.controller.subscribe('game', 'GAME_CHANGED', message => {
            this.menu.current.setGames(message);
        });
        this.controller.subscribe('game', 'SCORE_CHANGED', message => {
            this.menu.current.setScore(message);
        });
        this.controller.subscribe('game', 'YOU_WIN', message => {
            this.openModal('You win');
        });
        this.controller.subscribe('game', 'YOU_LOSE', message => {
            this.openModal('You lose');
        });
        this.controller.subscribe('game', 'DRAW', message => {
            this.openModal('Draw');
        });
        this.controller.subscribe('game', 'LEAVE_GAME', message => {
            this.controller.goLobbySearch();
        });
    }

    openModal(message) {
        this.setState({modalMode: true});
        this.modal.current.setMessage(message);
    }

    applyGameState(board) {
        this.controller.applyGameState(board);
    }

    leaveGame() {
        this.controller.leaveGame();
    }

    render() {
        return (
            <div className="w-100 h-100">
                <GameMenu ref={this.menu} onLeave={this.leaveGame.bind(this)}/>
                <Table ref={this.table} onBoardChanged={this.applyGameState.bind(this)} />
                {this.state.modalMode && <MessageWindow ref={this.modal} onOk={() => this.controller.goLobbySearch()}/>}
            </div>
        )
    }
}

export default Game;