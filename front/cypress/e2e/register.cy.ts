
describe('Register spec', () => {

    beforeEach(() => {
        cy.visit('/register');
    })

    it('Register successfull', () => {  

    cy.intercept('POST', '/api/auth/register', {
        body: {
            id: 1,
            username: 'userName',
            firstName: 'firstName',
            lastName: 'lastName',
            admin: true
        },
    })

    cy.get('input[formControlName=email]').type("test@gmail.com")
    cy.get('input[formControlName=firstName]').type("firstName")
    cy.get('input[formControlName=lastName]').type("lastName")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/login')
});

    it ('Registre failed', () => {
        cy.fixture('invalidRegister').then((register) => {
        cy.get('input[formControlName=email]').type(register.email)
        cy.get('input[formControlName=firstName]').type(register.firstName)
        cy.get('input[formControlName=lastName]').type(register.lastName)
        cy.get('input[formControlName=password]').type(register.password + "{enter}{enter}")
        })

        cy.url().should('include', '/register')
        cy.get(':button').should('be.disabled')
    })
});
