
import React, {Component} from 'react';

interface LocationPickerProps {
    text:string;
    onDraw(parsedLocation:string[]):void;
    onChange(location:string): void;  // called when a new location is picked
}

class LocationPicker extends Component<LocationPickerProps> {

    onTextChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {


        this.props.onChange(event.target.value);
    };
    onClear=()=>{
        this.props.onDraw([]);
    }
    onTextDraw=()=>{
        const texting = this.props.text;
        let parsedLocation = texting.split(",");
        let warning:string="";
        if(parsedLocation.length<2){
            warning+="Missing a portion of the line, or missing a comma.\n"
        }else if(parsedLocation.length>2){
            warning+="Extra portion of the line, or an extra comma.\n"
        }
        if(warning.length!=0){
            alert("There is an error with your line input. \nFor reference, the correct form "+
                "is: start,end (both in short names and capital letters)\n\n")
        }
        this.props.onDraw(parsedLocation);
    }
    render() {
        return (
            <div id="starting-location-picker">

                Path start from:<br/>
                <textarea
                    rows={5}
                    cols={30}
                    value={this.props.text}
                    onChange={this.onTextChange}
                /><br/>
                <button onClick={this.onTextDraw}>Draw</button>
                <button onClick={this.onClear}>Clear</button>

            </div>
        );
    }
}

export default LocationPicker;
