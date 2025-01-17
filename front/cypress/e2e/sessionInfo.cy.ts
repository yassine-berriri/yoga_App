describe('session info spec', ()=> {

    beforeEach(()=> {

        cy.fixture('sessions').then((sessions) => {
            cy.intercept({method: 'GET', url: '/api/session'}, sessions).as('sessions')
        })
    
        cy.fixture('session').then((session) => {
            cy.intercept({method: 'GET', url: 'api/session/2'}, session).as('session')
        })
        
        cy.fixture('teacher').then((teacher) => {
            cy.intercept({method: 'GET', url: '/api/teacher/2'}, teacher).as('teacher')
        })
    
    })

    it('diplay session info if user is admin', ()=> {
        cy.adminLogin()

        cy.contains('span', 'Detail').click();

        cy.fixture('teacher').then((teacher) => {
            cy.get('mat-card-subtitle').contains(`${teacher.firstName} ${teacher.lastName.toUpperCase()}`)
        })


        cy.fixture('session').then((session) => {
            cy.get('mat-card-content .ml1').contains(`${session.users.length} attendees`)
            cy.get('mat-card-content .description').contains(`Description: ${session.description}`)

        })

        cy.contains('button','Delete').should('exist')


    })

    it('diplay session info if user is not admin', ()=> {
        cy.login()

        cy.contains('span', 'Detail').click();

        cy.fixture('teacher').then((teacher) => {
            cy.get('mat-card-subtitle').contains(`${teacher.firstName} ${teacher.lastName.toUpperCase()}`)
        })


        cy.fixture('session').then((session) => {
            cy.get('mat-card-content .ml1').contains(`${session.users.length} attendees`)
            cy.get('mat-card-content .description').contains(`Description: ${session.description}`)

        })

        cy.contains('button','Participate').should('exist')


    })

    /*
    it('participate in session with success if user is not admin', ()=> {
        cy.login()



        cy.contains('span', 'Detail').click();

        cy.fixture('teacher').then((teacher) => {
            cy.get('mat-card-subtitle').contains(`${teacher.firstName} ${teacher.lastName.toUpperCase()}`)
        })


        cy.fixture('session').then((session) => {
            cy.get('mat-card-content .ml1').contains(`${session.users.length} attendees`)
            cy.get('mat-card-content .description').contains(`Description: ${session.description}`)

        })
        
        cy.contains('button','Participate').should('exist')

        cy.intercept({method: 'POST', url: '/api/session/2/participate/2'}, {})

        cy.contains('span', 'Participate').click();

        cy.get('button[color="warn"]').contains('Do not participate').should('exist');


    })
        */
})