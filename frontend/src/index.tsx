import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import {Button, Chip} from "@mui/material";

enum CellStatus { X = 'X', O = 'O', E = 'E' }

enum Player { X = 'X', O = 'O' }

type GameMove = {
    player: Player,
    table: CellStatus[][],
    winner: Player | null,
    valid: boolean,
    draw: boolean,
    gameOver: boolean,
}

const newGame = () => axios.get('http://localhost:8080/startGame').then(res => res.data);

const makeMove = (i: number, j: number) => axios.post(`http://localhost:8080/move/${i}/${j}`).then(res => res.data);


const Game = () => {
    const [move, setMove] = React.useState<GameMove | null>(null);

    React.useEffect(() => void newGame().then(setMove), []);

    const Cell = ({cell, row, col}: { cell: CellStatus, row: number, col: number }) => {
        return <Button
            onClick={() => cell === CellStatus.E && makeMove(row, col).then(setMove)}
            color={cell == CellStatus.X ? 'error' : cell == CellStatus.O ? 'success' : 'primary'}
            style={{width: '150px', height: '150px', fontSize: "30px"}}
            variant="contained">
            {cell == CellStatus.X ? 'X' : cell == CellStatus.O ? 'O' : '-'}
        </Button>;
    }

    const Result = ({winner}: { winner: Player | null }) => <>
        <Chip label={winner === null ? 'Draw' : winner === Player.X ? 'X wins' : 'O wins'}
              color={winner === null ? 'default' : winner === Player.X ? 'error' : 'success'}
              variant="outlined"
              style={{margin: '20px', fontSize: "30px"}}/>
        <Button
            onClick={() => newGame().then(setMove)}
            color="primary"
            variant="contained"
            style={{margin: '20px'}}>
            New Game
        </Button>
    </>

    const CurrentPlayer = ({player}: { player: Player }) =>
        <Chip label={player === Player.X ? '   X   ' : '   O   '}
              color={player === Player.X ? 'error' : 'success'}
              variant="outlined"
              style={{margin: '20px', fontSize: "30px"}}/>

    if (!move)
        return <div>Caricamento...</div>;

    return <div style={{textAlign: "center"}}>
        <div>
            <Cell cell={move.table[0][0]} row={0} col={0}/>
            <Cell cell={move.table[0][1]} row={0} col={1}/>
            <Cell cell={move.table[0][2]} row={0} col={2}/>
        </div>
        <div>
            <Cell cell={move.table[1][0]} row={1} col={0}/>
            <Cell cell={move.table[1][1]} row={1} col={1}/>
            <Cell cell={move.table[1][2]} row={1} col={2}/>
        </div>
        <div>
            <Cell cell={move.table[2][0]} row={2} col={0}/>
            <Cell cell={move.table[2][1]} row={2} col={1}/>
            <Cell cell={move.table[2][2]} row={2} col={2}/>
        </div>

        <div>
            {move.gameOver && < Result winner={move.winner}/>}
        </div>

        <div>
            <CurrentPlayer player={move.player}/>
        </div>

    </div>
}

ReactDOM.render(
  <React.StrictMode>
      <Game/>
  </React.StrictMode>,
  document.getElementById('root')
);

