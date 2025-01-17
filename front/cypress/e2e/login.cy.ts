
describe('Login spec', () => {

  beforeEach(() => {
    cy.visit('/login');
  })

  it('Login successfull', () => {
    

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')
    
    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')
  })


  it ('Login failed', ()=> {
    cy.fixture('invalidLogin').then((login) => {
      cy.intercept({method: 'POST', url: '/api/auth/login'},{
        statusCode: 401,
        body: {
          login
        }
      }).as('invalidLogin')

      cy.get('input[formControlName=email]').type("invalidLogin@test.com")
      cy.get('input[formControlName=password]').type(`${"invalidLogin"}{enter}{enter}`)

      cy.url().should('include', '/login')
      cy.get('.error').contains('An error occurred')
    })
  })

});