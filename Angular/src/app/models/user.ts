export class User {
    'id': number;
    'firstName': string;
    'lastName': string;
    'email': string;
    'imgHash': string;
    
    constructor (id: number, firstName: string, lastName: string, email: string, imgHash: string){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.imgHash = imgHash;
    }

    getId(): number{
        return this.id;
    }

    getFirstName(): string{
        return this.firstName;
    }

    getLastName(): string{
        return this.lastName;
    }

    getEmail(): string{
        return this.email;
    }

    getImgHash(): string{
        return this.imgHash;
    }
}