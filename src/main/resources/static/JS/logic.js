const keySearch = async (value)=>{
    console.log(value);
    let result = await fetch(`http://localhost:8080/search/${value}`);
    console.log(await result.json());
}
