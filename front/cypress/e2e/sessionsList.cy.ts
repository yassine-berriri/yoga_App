describe('Session List spec ', () => {

    beforeEach(()=> {
        cy.fixture('sessions').then((sessions) => {
            cy.intercept({method: 'GET', url: '/api/session'}, sessions).as('session')
        })



    })

    it('display session list if user is admin', () => {
        cy.adminLogin()
        
        cy.get('.item').should('have.length', 2)

        cy.get(`button[routerLink="create"]`).should('exist')
        cy.contains('button','Edit').should('exist')
        
    })

    it('Absence of Create and Edit button if user is not admin', () => {
        cy.login()

        cy.get(`button[routerLink="create"]`).should('not.exist')
        cy.contains('button','Edit').should('not.exist')
    })
})