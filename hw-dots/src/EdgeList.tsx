/*
 * Copyright (C) 2021 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';


interface EdgeListProps {
    text:string;
    size:number;
    parsedText:string[][];
    onChange(parsedText:string[][],edges: string): void;  // called when a new edge list is ready
                                 // once you decide how you want to communicate the edges to the App, you should
                                 // change the type of edges so it isn't `any`
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps> {
    onTextChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {

        let lines = event.target.value;
        let parsedText = new Array();
        parsedText = this.props.parsedText;
        this.props.onChange(this.props.parsedText,lines);
    };

    onDraw=() =>{
        const texting = this.props.text;
        var lines = texting.split("\n");
        var parsedText1 = new Array(lines.length);
        for(let i = 0 ; i < lines.length;i++){
            parsedText1[i] = lines[i].split(" ");
        }
        let parsedText : string[][] = parsedText1; //parsing the data [[[x1,y1],[x2,y2],[color]],...]
        let warning:string = "";
        let result:string[] = new Array(lines.length);
        for(let i = 0 ; i < parsedText.length ; i ++) {
            let start :string[]= parsedText[i][0].split(",");
            let end :string[]= parsedText[i][1].split(",");
            let color = parsedText[i][2];
            //A lot of checks start here!
            if (parsedText[i].length < 3 ) {
                warning+="Line" + (i + 1) + ": Missing a portion of the line, or missing a space.\n"
            } else if(parsedText[i].length >3){
                warning+="Line" + (i + 1) + ": Extra portion of the line, or an extra space.\n"
            }
            if(start.length!=2 ){
                warning+="Line" +(i+ 1) + ":Wrong number of parts to the first coordinate.\n"
            }
            if(end.length!=2){
                warning += "Line" +(i+ 1) + ":Wrong number of parts to the second coordinate.\n"

            }
            let startX = parseInt(start[0]);
            let startY = parseInt(start[1]);
            let endX = parseInt(end[0]);
            let endY = parseInt(end[1]);
            if(isNaN(startX)|| isNaN(startY)||isNaN(endX)||isNaN(endY)){
                warning +="Line" +(i+ 1) + ":Coordinate(s) contain non-integer value(s).\n"

            }else if(startX>this.props.size-1 ||startY>this.props.size-1||endX>this.props.size-1||endY>this.props.size-1){
                warning+="Line" +(i+ 1) + ":Cannot draw edges, grid must be at least size "+
                    (Math.max(startX,startY,endX,endY)+1)+"\n";

            }
            let sublist = new Array(5);


        }
        if(warning.length!=0){
            let title:string = "There was an error with some of your line input. \nFor reference, the correct form " +
                "for each line is: x1,y1 x2,y2 color\n\n";
            alert(title+warning);
        }
        parsedText = this.props.parsedText;
    }
    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={this.onTextChange}
                    value={this.props.text}
                /> <br/>
                <button onClick={this.onDraw}>Draw</button>
                <button onClick={() => {console.log('Clear onClick was called');}}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;
