export function extractNewTags(idTags, unsignedTags) {
    let newTags = []
    unsignedTags.filter(t => !t.id).forEach(uT => {
        idTags.forEach(iT => {
            if (iT.name === uT.name) {
                newTags.push(iT)
            }
        })
    })
    return newTags
}