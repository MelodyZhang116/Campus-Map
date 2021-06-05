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
import MapView from "./MapView";
import LocationPicker from "./LocationPicker";

interface AppState{
    start:string;  //a starting location
    end:string;    //an ending location
    path:[number,number][]; //the coordinates of points that are contained in the
                            // shortest path from start to end
    buildings:Record<string, string> //buildings<shortName, longName>

}
class App extends Component<{}, AppState> {
    constructor(props:any) {
        super(props);
        this.state = {
            start:"",
            end:"",
            path: [],
            buildings:{},
        }
        this.getBuildings();
    }

    updateStart = (start:string)=>{
        const buildings = this.state.buildings;
        this.setState({
            start:Object.keys(buildings).find(short =>buildings[short]===start)!
        })
    }
    updateEnd =(end:string)=>{
        const buildings = this.state.buildings;
        this.setState({
            end:Object.keys(buildings).find(short =>buildings[short]===end)!
        })
    }
    getBuildings = async() =>{
        try{
            let response = await fetch("http://localhost:4567/buildings");
            if(!response.ok){
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }
            let object = (await response.json())as Record<string,string>;
            this.setState({
                buildings:object
            })

        }catch(e){
            alert("There was an error contacting the server.");
            console.log(e);
        }
    }
    findPath= async () => {
        try {

            let response = await fetch("http://localhost:4567/path?start="+this.state.start
            +"&end="+this.state.end);

            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }

            let object = (await response.json())as [number,number][];
            this.setState({
                path:object,
            })
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };
    clear=()=>{
        this.setState({
            path:[],
            start:"",
            end:"",
        })
    }

    render() {
        return (
            <div>
                <LocationPicker buildings={this.state.buildings} onStart={this.updateStart} onEnd={this.updateEnd}/>
                <button onClick={this.findPath}>Draw the path</button>
                <button onClick={this.clear}>Clear</button>
                <MapView path={this.state.path}/>
            </div>
        );
    }

}

export default App;
