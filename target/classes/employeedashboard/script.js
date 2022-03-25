let userId;

window.onload = function(){

    //this is used to retrieve query param
    const params = new Proxy(new URLSearchParams(window.location.search), {
        get: (searchParams, prop) => searchParams.get(prop),
      });

    userId = params.userId; // "some_value"


    console.log(userId)

    getAllLists()

    //createList({id: 30, name: "Buccees", userIdFk: 40})
}


async function getAllLists(){

    /* let listContainerElem = document.getElementById("list-container");
    listContainerElem.innerHTML = "";  */
    
    let response = await fetch(`${domain}/list?userId=${userId}`);

    let responseBody = await response.json();

    console.log(responseBody)

    let lists = responseBody.data;


    lists.forEach(list => {
        createList(list)
    });


}


function createList(list){

    let listContainerElem = document.getElementById("list-container");

    let listCardElem = document.createElement("div");
    listCardElem.className = "list-card"

    listCardElem.innerHTML = `
        <div class="list-title">${list.name}</div>
        <div class="img-container">
            <img src="./cart.png" alt="cart">
        </div>
        <div class="list-btns">
            <button id="view-btn-${list.id}" class="btn btn-primary" onclick=viewList(event)>View</button>
            <button id="delete-btn-${list.id}" class="delete-btn" onclick=deleteList(event)><div>x</div></button>
        </div>`

    listContainerElem.appendChild(listCardElem);

}


async function createNewList(event){
    event.preventDefault();

    let createListInputElem = document.getElementById("list-name");

    //send request to create list
    let list = {id: 0, name: createListInputElem.value, userId: userId}
    
    let response = await fetch(`${domain}/list`,{
        method: "POST",
        body: JSON.stringify(list)
    })

    let responseBody = await response.json();
    

    console.log(responseBody)

    createList(responseBody.data);

    createListInputElem.value=""
}


async function deleteList(event){


    //retrieve list id
    let elem;

    if(event.target.className != "delete-btn"){
        elem = event.target.parentNode
    }else{   
        elem = event.target
    }

    let listId = elem.id.substring("delete-btn-".length)


    let response = await fetch(`${domain}/list/${listId}`,{
        method: "DELETE"
    })

    let responseBody = await response.json()

    let listElem = elem.parentNode.parentNode;
    listElem.remove()

    console.log(responseBody)

    // getAllLists()

}


function viewList(event){

    let viewBtn = event.target;

    let listId = viewBtn.id.substring("view-btn-".length);


    window.location = `../grocerylist?listId=${listId}`

}