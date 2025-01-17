describe('Session modification spec', () => {
    beforeEach(() => {
        cy.fixture('sessions').then((sessions) => {
            cy.intercept({method: 'GET', url: '/api/session'}, sessions).as('sessions')
        })
        
        cy.adminLogin()

        cy.fixture('session').then((session) => {
            cy.intercept({method: 'GET', url: '/api/session/2'}, session).as('session')
        })
        cy.fixture('teachers').then((teachers) => {
            cy.intercept({method: 'GET', url: '/api/teacher'}, teachers).as('teachers')
        })

        cy.contains('span', 'Edit').click()
    })

    it('Session updated successfully', () => {
    
        cy.fixture('sessionUpdated').then((session) => {
            cy.intercept({method: 'PUT', url: '/api/session/2'}, session).as('sessionUpdated')
        })

        cy.get('button[type=submit]').click()

        cy.url().should('include', '/sessions')
        cy.get('.mat-simple-snack-bar-content').contains("Session updated !")
    })

    it('Display error if missing required form field', () => {
        cy.get('input[formControlName=name]').clear()

        cy.contains('span', 'Save').click()
        cy.get('button[type=submit]').should('be.disabled')
    })
});